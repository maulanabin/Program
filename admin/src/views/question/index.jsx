import React, { Component } from "react";
import { Card, Button, Table, message, Divider } from "antd";
import {
  getQuestions,
  deleteQuestion,
  editQuestion,
  addQuestion,
} from "@/api/question";
import TypingCard from "@/components/TypingCard";
import EditQuestionForm from "./forms/edit-question-form";
import AddQuestionForm from "./forms/add-question-form";
import { withRouter } from "react-router";
import { Link } from "react-router-dom";

const { Column } = Table;

class Question extends Component {
  state = {
    questions: [],
    editQuestionModalVisible: false,
    editQuestionModalLoading: false,
    currentRowData: {},
    addQuestionModalVisible: false,
    addQuestionModalLoading: false,
    rpsDetailID: "",
    rpsID: "",
  };
  getQuestions = async (rpsDetailID) => {
    const result = await getQuestions(rpsDetailID);
    const { content, statusCode } = result.data;
    if (statusCode === 200) {
      this.setState({
        questions: content,
      });
    }
  };

  handleEditQuestion = (row) => {
    this.setState({
      currentRowData: Object.assign({}, row),
      editQuestionModalVisible: true,
    });
  };

  handleDeleteQuestion = (row) => {
    const { id } = row;
    if (id === "admin") {
      message.error("不能Hapus Data管理员用户！");
      return;
    }
    deleteQuestion({ id }).then((res) => {
      message.success("Hapus Data Berhasil");
      this.getQuestions(this.state.rpsDetailID);
    });
  };

  handleEditQuestionOk = (_) => {
    const { form } = this.editQuestionFormRef.props;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      this.setState({ editModalLoading: true });
      editQuestion(values)
        .then((response) => {
          form.resetFields();
          this.setState({
            editQuestionModalVisible: false,
            editQuestionModalLoading: false,
          });
          message.success("Berhasil Memperbarui Data!");
          this.getQuestions(this.state.rpsDetailID);
        })
        .catch((e) => {
          message.success("Pengeditan gagal, harap coba lagi!");
        });
    });
  };

  handleCancel = (_) => {
    this.setState({
      editQuestionModalVisible: false,
      addQuestionModalVisible: false,
    });
  };

  handleAddQuestion = (row) => {
    this.setState({
      addQuestionModalVisible: true,
    });
  };

  handleAddQuestionOk = () => {
    const { form } = this.addQuestionFormRef.props;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      this.setState({ addQuestionModalLoading: true });
      const { file, ...otherValues } = values;
      if (file !== undefined) {
        const formData = new FormData();
        formData.append("file", file.fileList[0].originFileObj);
        formData.append("rps_detail_id", this.props.match.params.rpsDetailID);
        formData.append("title", otherValues.title);
        formData.append("description", otherValues.description);
        formData.append("question_type", otherValues.question_type);
        formData.append("answer_type", otherValues.answer_type);

        addQuestion(formData)
          .then((response) => {
            form.resetFields();
            this.setState({
              addQuestionModalVisible: false,
              addQuestionModalLoading: false,
            });
            message.success("Berhasil Menambahkan Data!");
            this.getQuestions(this.state.rpsDetailID);
          })
          .catch((e) => {
            this.setState({
              addQuestionModalLoading: false,
            });
            message.success("Gagal menambahkan, harap coba lagi!");
          });
      } else {
        const formData = new FormData();
        formData.append("rps_detail_id", this.props.match.params.rpsDetailID);
        formData.append("title", otherValues.title);
        formData.append("description", otherValues.description);
        formData.append("question_type", otherValues.question_type);
        formData.append("answer_type", otherValues.answer_type);
        addQuestion(formData)
          .then((response) => {
            form.resetFields();
            this.setState({
              addQuestionModalVisible: false,
              addQuestionModalLoading: false,
            });
            message.success("Berhasil Menambahkan Data!");
            this.getQuestions(this.state.rpsDetailID);
          })
          .catch((e) => {
            this.setState({
              addQuestionModalLoading: false,
            });
            message.success("Gagal menambahkan, harap coba lagi!");
          });
      }
    });
  };
  componentDidMount() {
    this.setState({
      rpsID: this.props.match.params.rpsID,
      rpsDetailID: this.props.match.params.rpsDetailID,
    });
    this.getQuestions(this.props.match.params.rpsDetailID);
  }
  render() {
    const { questions, rpsID, rpsDetailID } = this.state;
    const title = (
      <span>
        <Button type="primary" onClick={this.handleAddQuestion}>
          Tambahkan pertanyaan
        </Button>
      </span>
    );
    const cardContent = `Di sini, Anda dapat mengelola pertanyaan di sistem, seperti menambahkan pertanyaan baru, atau mengubah pertanyaan yang sudah ada di sistem.`;
    return (
      <div className="app-container">
        <TypingCard title="Manajemen Pertanyaan" source={cardContent} />
        <br />
        <Card title={title}>
          <Table bordered rowKey="id" dataSource={questions} pagination={false}>
            <Column
              title="ID Pertanyaan"
              dataIndex="id"
              key="id"
              align="center"
            />
            <Column
              title="Pertanyaan"
              dataIndex="title"
              key="title"
              align="center"
            />
            <Column
              title="Deskripsi Pertanyaan"
              dataIndex="description"
              key="description"
              align="center"
            />
            <Column
              title="Tipe Jawaban"
              dataIndex="answerType"
              key="answerType"
              align="center"
            />
            <Column
              title="Tipe Soal"
              dataIndex="questionType"
              key="questionType"
              align="center"
            />
            <Column
              title="Operasi"
              key="action"
              width={195}
              align="center"
              render={(text, row) => (
                <span>
                  <Button
                    type="primary"
                    shape="circle"
                    icon="edit"
                    title="Edit Data"
                    onClick={this.handleEditQuestion.bind(null, row)}
                  />
                  <Divider type="vertical" />
                  <Link to={`/rps/${rpsID}/${rpsDetailID}/${row.id}`}>
                    <Button
                      type="primary"
                      shape="circle"
                      icon="diff"
                      title="Tambahkan Jawaban"
                    />
                  </Link>
                  <Divider type="vertical" />
                  <Button
                    type="primary"
                    shape="circle"
                    icon="delete"
                    title="Hapus Data"
                    onClick={this.handleDeleteQuestion.bind(null, row)}
                  />
                </span>
              )}
            />
          </Table>
        </Card>
        <EditQuestionForm
          currentRowData={this.state.currentRowData}
          wrappedComponentRef={(formRef) =>
            (this.editQuestionFormRef = formRef)
          }
          visible={this.state.editQuestionModalVisible}
          confirmLoading={this.state.editQuestionModalLoading}
          onCancel={this.handleCancel}
          onOk={this.handleEditQuestionOk}
        />
        <AddQuestionForm
          wrappedComponentRef={(formRef) => (this.addQuestionFormRef = formRef)}
          visible={this.state.addQuestionModalVisible}
          confirmLoading={this.state.addQuestionModalLoading}
          onCancel={this.handleCancel}
          onOk={this.handleAddQuestionOk}
        />
      </div>
    );
  }
}

export default withRouter(Question);
