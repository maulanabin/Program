import React, { Component } from "react";
import { Card, Button, Table, message, Divider } from "antd";
import { getExam, deleteExam, editExam, addExam } from "@/api/exam";
import { getQuestions } from "@/api/question";
import { getRPS } from "@/api/rps";

import { Link } from "react-router-dom";
import TypingCard from "@/components/TypingCard";
import EditExamForm from "./forms/edit-exam-form";
import AddExamForm from "./forms/add-exam-form";
import { getQuestionsByRPS } from "../../api/question";
import moment from "moment";
const { Column } = Table;
class Exam extends Component {
  state = {
    exam: [],
    questions: [],
    rps: [],
    editExamModalVisible: false,
    editExamModalLoading: false,
    currentRowData: {},
    addExamModalVisible: false,
    addExamModalLoading: false,
  };
  getExam = async () => {
    const result = await getExam();
    const { content, statusCode } = result.data;
    console.log(result.data);
    if (statusCode === 200) {
      this.setState({
        exam: content,
      });
    }
  };
  getQuestions = async () => {
    const result = await getQuestions();
    const { content, statusCode } = result.data;

    if (statusCode === 200) {
      this.setState({
        questions: content,
      });
    }
  };
  updateQuestion = async (id) => {
    const result = await getQuestionsByRPS(id);
    const { content, statusCode } = result.data;

    if (statusCode === 200) {
      this.setState({
        questions: content,
      });
    }
  };
  getRps = async () => {
    const result = await getRPS();
    const { content, statusCode } = result.data;

    if (statusCode === 200) {
      this.setState({
        rps: content,
      });
    }
  };
  handleEditExam = (row) => {
    this.setState({
      currentRowData: Object.assign({}, row),
      editExamModalVisible: true,
    });
  };

  handleDeleteExam = (row) => {
    const { id } = row;
    if (id === "admin") {
      message.error("不能Hapus Data管理员用户！");
      return;
    }
    deleteExam({ id }).then((res) => {
      message.success("Hapus Data Berhasil");
      this.getExam();
    });
  };

  handleEditExamOk = (_) => {
    const { form } = this.editExamFormRef.props;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      this.setState({ editModalLoading: true });
      editExam(values, values.id)
        .then((response) => {
          form.resetFields();
          this.setState({
            editExamModalVisible: false,
            editExamModalLoading: false,
          });
          message.success("Berhasil Memperbarui Data!");
          this.getExam();
        })
        .catch((e) => {
          message.success("Pengeditan gagal, harap coba lagi!");
        });
    });
  };

  handleCancel = (_) => {
    this.setState({
      editExamModalVisible: false,
      addExamModalVisible: false,
    });
  };

  handleAddExam = (row) => {
    this.setState({
      addExamModalVisible: true,
    });
  };

  handleAddExamOk = (_) => {
    const { form } = this.addExamFormRef.props;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      this.setState({ addExamModalLoading: true });
      addExam(values)
        .then((response) => {
          form.resetFields();
          this.setState({
            addExamModalVisible: false,
            addExamModalLoading: false,
          });
          message.success("Berhasil Menambahkan Data!");
          this.getExam();
        })
        .catch((e) => {
          message.success("添加失败,请重试!");
        });
    });
  };
  componentDidMount() {
    this.getExam();
    this.getQuestions();
    this.getRps();
  }
  render() {
    const { exam, questions, rps } = this.state;
    const title = (
      <span>
        <Button type="primary" onClick={this.handleAddExam}>
          Tambahkan Ujian
        </Button>
      </span>
    );
    const cardContent = `Di sini, Anda dapat mengelola Exam sesuai dengan mata kuliah yang diampu. Di bawah ini dapat menampilkan list Exam yang ada.`;
    return (
      <div className="app-container">
        <TypingCard title="Manajemen Ujian" source={cardContent} />
        <br />
        <Card title={title}>
          <Table bordered rowKey="id" dataSource={exam} pagination={false}>
            <Column title="Nama" dataIndex="name" key="name" align="center" />
            <Column
              title="RPS"
              dataIndex="rps.name"
              key="rps.name"
              align="center"
            />
            <Column
              title="Nilai Minimal"
              dataIndex="min_grade"
              key="min_grade"
              align="center"
            />
            <Column
              title="Tanggal Mulai"
              dataIndex="date_start"
              key="date_start"
              align="center"
              render={(text) => moment(text).format("DD MMMM YYYY, HH:mm:ss")}
            />
            <Column
              title="Tanggal Selesai"
              dataIndex="date_end"
              key="date_end"
              align="center"
              render={(text) => moment(text).format("DD MMMM YYYY, HH:mm:ss")}
            />
            <Column
              title="Durasi"
              dataIndex="duration"
              key="duration"
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
                    title="Edit Ujian"
                    onClick={this.handleEditExam.bind(null, row)}
                  />
                  <Divider type="vertical" />
                  <Link to={`/setting-exam/result/${row.id}`}>
                    <Button
                      type="primary"
                      shape="circle"
                      icon="diff"
                      title="Detail Hasil"
                    />
                  </Link>
                  <Divider type="vertical" />
                  <Button
                    type="primary"
                    shape="circle"
                    icon="delete"
                    title="Hapus Data"
                    onClick={this.handleDeleteExam.bind(null, row)}
                  />
                </span>
              )}
            />
          </Table>
        </Card>
        <EditExamForm
          currentRowData={this.state.currentRowData}
          wrappedComponentRef={(formRef) => (this.editExamFormRef = formRef)}
          visible={this.state.editExamModalVisible}
          confirmLoading={this.state.editExamModalLoading}
          onCancel={this.handleCancel}
          onOk={this.handleEditExamOk}
          handleUpdateQuestion={this.updateQuestion}
          questions={questions}
          rpsAll={rps}
        />
        <AddExamForm
          wrappedComponentRef={(formRef) => (this.addExamFormRef = formRef)}
          visible={this.state.addExamModalVisible}
          confirmLoading={this.state.addExamModalLoading}
          onCancel={this.handleCancel}
          onOk={this.handleAddExamOk}
          handleUpdateQuestion={this.updateQuestion}
          questions={questions}
          rps={rps}
        />
      </div>
    );
  }
}

export default Exam;
