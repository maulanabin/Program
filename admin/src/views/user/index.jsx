import React, { Component } from "react";
import { Card, Button, Table, message } from "antd";
import { getUsers, deleteUser, editUser, addUser } from "@/api/user";
import TypingCard from "@/components/TypingCard";
import EditUserForm from "./forms/edit-user-form";
import AddUserForm from "./forms/add-user-form";
const { Column } = Table;
class User extends Component {
  state = {
    users: [],
    editUserModalVisible: false,
    editUserModalLoading: false,
    currentRowData: {},
    addUserModalVisible: false,
    addUserModalLoading: false,
  };
  getUsers = async () => {
    const result = await getUsers();
    const { content, statusCode } = result.data;
    if (statusCode === 200) {
      this.setState({
        users: content,
      });
    }
  };
  handleEditUser = (row) => {
    this.setState({
      currentRowData: Object.assign({}, row),
      editUserModalVisible: true,
    });
  };

  handleDeleteUser = (row) => {
    const { id } = row;
    if (id === "admin") {
      message.error("不能Hapus Data管理员用户！");
      return;
    }
    deleteUser({ id }).then((res) => {
      message.success("Hapus Data Berhasil");
      this.getUsers();
    });
  };

  handleEditUserOk = (_) => {
    const { form } = this.editUserFormRef.props;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      this.setState({ editModalLoading: true });
      editUser(values)
        .then((response) => {
          form.resetFields();
          this.setState({
            editUserModalVisible: false,
            editUserModalLoading: false,
          });
          message.success("Berhasil memperbarui pengguna!");
          this.getUsers();
        })
        .catch((e) => {
          this.setState({
            editUserModalLoading: false,
          });
          message.success("Pengeditan gagal, harap coba lagi!");
        });
    });
  };

  handleCancel = (_) => {
    this.setState({
      editUserModalVisible: false,
      addUserModalVisible: false,
    });
  };

  handleAddUser = (row) => {
    this.setState({
      addUserModalVisible: true,
    });
  };

  handleAddUserOk = (_) => {
    const { form } = this.addUserFormRef.props;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      this.setState({ addUserModalLoading: true });
      addUser(values)
        .then((response) => {
          form.resetFields();
          this.setState({
            addUserModalVisible: false,
            addUserModalLoading: false,
          });
          message.success("Berhasil mendaftarkan pengguna!");
          this.getUsers();
        })
        .catch((e) => {
          this.setState({
            addUserModalLoading: false,
          });
          const errorMessage = e.response.data.message || "Terjadi kesalahan";
          message.error(errorMessage);
        });
    });
  };
  componentDidMount() {
    this.getUsers();
  }
  render() {
    const { users } = this.state;
    const title = (
      <span>
        <Button type="primary" onClick={this.handleAddUser}>
          Tambahkan pengguna
        </Button>
      </span>
    );
    const cardContent = `Di sini, Anda dapat mengelola pengguna di sistem, seperti menambahkan pengguna baru, atau mengubah pengguna yang sudah ada di sistem.。`;
    return (
      <div className="app-container">
        <TypingCard title="Manajemen Pengguna" source={cardContent} />
        <br />
        <Card title={title}>
          <Table bordered rowKey="id" dataSource={users} pagination={false}>
            <Column
              title="ID Pengguna"
              dataIndex="id"
              key="id"
              align="center"
            />
            <Column title="Nama" dataIndex="name" key="name" align="center" />
            <Column
              title="Username"
              dataIndex="username"
              key="username"
              align="center"
            />
            <Column
              title="Email"
              dataIndex="email"
              key="email"
              align="center"
            />
            <Column
              title="Peran"
              dataIndex="roles"
              key="roles"
              align="center"
            />
            <Column
              title="Operasi"
              key="action"
              width={195}
              align="center"
              render={(text, row) => (
                <span>
                  {/* <Button
                    type="primary"
                    shape="circle"
                    icon="edit"
                    title="Edit Data"
                    onClick={this.handleEditUser.bind(null, row)}
                  /> */}
                  {/* <Divider type="vertical" /> */}
                  <Button
                    type="primary"
                    shape="circle"
                    icon="delete"
                    title="Hapus Data"
                    onClick={this.handleDeleteUser.bind(null, row)}
                  />
                </span>
              )}
            />
          </Table>
        </Card>
        <EditUserForm
          currentRowData={this.state.currentRowData}
          wrappedComponentRef={(formRef) => (this.editUserFormRef = formRef)}
          visible={this.state.editUserModalVisible}
          confirmLoading={this.state.editUserModalLoading}
          onCancel={this.handleCancel}
          onOk={this.handleEditUserOk}
        />
        <AddUserForm
          wrappedComponentRef={(formRef) => (this.addUserFormRef = formRef)}
          visible={this.state.addUserModalVisible}
          confirmLoading={this.state.addUserModalLoading}
          onCancel={this.handleCancel}
          onOk={this.handleAddUserOk}
        />
      </div>
    );
  }
}

export default User;
