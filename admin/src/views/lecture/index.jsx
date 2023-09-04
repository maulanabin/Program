import React, { Component } from "react";
import { Card, Button, Table, message, Divider } from "antd";
import {
  getLectures,
  deleteLecture,
  editLecture,
  addLecture,
} from "@/api/lecture";
import { getReligions } from "@/api/religion";
import { getUsersNotUsedInLectures } from "@/api/user";
import { getStudyPrograms } from "@/api/studyProgram";

import TypingCard from "@/components/TypingCard";
import EditLectureForm from "./forms/edit-lecture-form";
import AddLectureForm from "./forms/add-lecture-form";
const { Column } = Table;
class Lecture extends Component {
  state = {
    lectures: [],
    religions: [],
    users: [],
    studyPrograms: [],
    editLectureModalVisible: false,
    editLectureModalLoading: false,
    currentRowData: {},
    addLectureModalVisible: false,
    addLectureModalLoading: false,
  };
  getLectures = async () => {
    const result = await getLectures();
    const { content, statusCode } = result.data;
    if (statusCode === 200) {
      this.setState({
        lectures: content,
      });
    }
  };

  getReligions = async () => {
    const result = await getReligions();
    const { content, statusCode } = result.data;

    if (statusCode === 200) {
      this.setState({
        religions: content,
      });
    }
  };

  getUsers = async () => {
    const result = await getUsersNotUsedInLectures();
    const { content, statusCode } = result.data;

    if (statusCode === 200) {
      this.setState({
        users: content,
      });
    }
  };

  getStudyPrograms = async () => {
    const result = await getStudyPrograms();
    const { content, statusCode } = result.data;

    if (statusCode === 200) {
      this.setState({
        studyPrograms: content,
      });
    }
  };

  handleEditLecture = (row) => {
    this.setState({
      currentRowData: Object.assign({}, row),
      editLectureModalVisible: true,
    });
  };

  handleDeleteLecture = (row) => {
    const { id } = row;
    if (id === "admin") {
      message.error("不能Hapus Data管理员用户！");
      return;
    }
    deleteLecture({ id }).then((res) => {
      message.success("Hapus Data Berhasil");
      this.getLectures();
      this.getUsers();
    });
  };

  handleEditLectureOk = (_) => {
    const { form } = this.editLectureFormRef.props;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      this.setState({ editModalLoading: true });
      editLecture(values, values.id)
        .then((response) => {
          form.resetFields();
          this.setState({
            editLectureModalVisible: false,
            editLectureModalLoading: false,
          });
          message.success("Berhasil Memperbarui Data!");
          this.getLectures();
          this.getUsers();
        })
        .catch((e) => {
          message.success("Pengeditan gagal, harap coba lagi!");
        });
    });
  };

  handleCancel = (_) => {
    this.setState({
      editLectureModalVisible: false,
      addLectureModalVisible: false,
    });
  };

  handleAddLecture = (row) => {
    this.setState({
      addLectureModalVisible: true,
    });
  };

  handleAddLectureOk = (_) => {
    const { form } = this.addLectureFormRef.props;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      this.setState({ addLectureModalLoading: true });
      addLecture(values)
        .then((response) => {
          form.resetFields();
          this.setState({
            addLectureModalVisible: false,
            addLectureModalLoading: false,
          });
          message.success("Berhasil Menambahkan Data!");
          this.getLectures();
          this.getUsers();
        })
        .catch((e) => {
          message.success("添加失败,请重试!");
        });
    });
  };
  componentDidMount() {
    this.getLectures();
    this.getReligions();
    this.getUsers();
    this.getStudyPrograms();
  }
  render() {
    const { lectures, religions, users, studyPrograms } = this.state;
    const title = (
      <span>
        <Button type="primary" onClick={this.handleAddLecture}>
          Tambahkan dosen
        </Button>
      </span>
    );
    const cardContent = `Di sini, Anda dapat mengelola dosen di sistem, seperti menambahkan dosen baru, atau mengubah dosen yang sudah ada di sistem.`;
    return (
      <div className="app-container">
        <TypingCard title="Manajemen Dosen" source={cardContent} />
        <br />
        <Card title={title}>
          <Table bordered rowKey="id" dataSource={lectures} pagination={false}>
            <Column title="NIDN" dataIndex="nidn" key="nidn" align="center" />
            <Column
              title="Nama Depan"
              dataIndex="name"
              key="name"
              align="center"
            />
            <Column
              title="Tempat Lahir"
              dataIndex="place_born"
              key="place_born"
              align="center"
            />
            <Column
              title="Agama"
              dataIndex="religion.name"
              key="religion.name"
              align="center"
            />
            <Column
              title="Telepon"
              dataIndex="phone"
              key="phone"
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
                    onClick={this.handleEditLecture.bind(null, row)}
                  />
                  <Divider type="vertical" />
                  <Button
                    type="primary"
                    shape="circle"
                    icon="delete"
                    title="Hapus Data"
                    onClick={this.handleDeleteLecture.bind(null, row)}
                  />
                </span>
              )}
            />
          </Table>
        </Card>
        <EditLectureForm
          currentRowData={this.state.currentRowData}
          wrappedComponentRef={(formRef) => (this.editLectureFormRef = formRef)}
          visible={this.state.editLectureModalVisible}
          confirmLoading={this.state.editLectureModalLoading}
          onCancel={this.handleCancel}
          onOk={this.handleEditLectureOk}
          religion={religions}
          user={users}
          studyProgram={studyPrograms}
        />
        <AddLectureForm
          wrappedComponentRef={(formRef) => (this.addLectureFormRef = formRef)}
          visible={this.state.addLectureModalVisible}
          confirmLoading={this.state.addLectureModalLoading}
          onCancel={this.handleCancel}
          onOk={this.handleAddLectureOk}
          religion={religions}
          user={users}
          studyProgram={studyPrograms}
        />
      </div>
    );
  }
}

export default Lecture;
