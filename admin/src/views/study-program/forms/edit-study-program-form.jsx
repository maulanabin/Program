import React, { Component } from "react";
import { Form, Input, Modal, Select } from "antd";
const { TextArea } = Input;
class EditStudyProgramForm extends Component {
  render() {
    const {
      visible,
      onCancel,
      onOk,
      form,
      confirmLoading,
      currentRowData,
      departments,
    } = this.props;
    const { getFieldDecorator } = form;
    const { id, name, description } = currentRowData;
    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
    };
    return (
      <Modal
        title="Edit Program Studi"
        visible={visible}
        onCancel={onCancel}
        onOk={onOk}
        confirmLoading={confirmLoading}
      >
        <Form {...formItemLayout}>
          <Form.Item label="ID Program Studi:">
            {getFieldDecorator("id", {
              initialValue: id,
            })(<Input disabled />)}
          </Form.Item>
          <Form.Item label="Jurusan:">
            {getFieldDecorator("department_id", {
              rules: [
                {
                  required: true,
                  message: "Silahkan isi jurusan program studi",
                },
              ],
            })(
              <Select style={{ width: 300 }} placeholder="Pilih Jurusan">
                {departments.map((arr, key) => {
                  return (
                    <Select.Option value={arr.id} key={key}>
                      {arr.name}
                    </Select.Option>
                  );
                })}
              </Select>
            )}
          </Form.Item>
          <Form.Item label="Nama Prodi:">
            {getFieldDecorator("name", {
              rules: [
                { required: true, message: "Silahkan isi nama program studi" },
              ],
              initialValue: name,
            })(<Input placeholder="Nama program studi" />)}
          </Form.Item>
          <Form.Item label="Deskripsi Prodi:">
            {getFieldDecorator("description", {
              initialValue: description,
            })(
              <TextArea
                rows={4}
                placeholder="Silahkan isi deskripsi program studi"
              />
            )}
          </Form.Item>
        </Form>
      </Modal>
    );
  }
}

export default Form.create({ name: "EditStudyProgramForm" })(
  EditStudyProgramForm
);
