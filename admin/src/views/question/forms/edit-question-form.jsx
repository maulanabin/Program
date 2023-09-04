import React, { Component } from "react";
import { Form, Icon, Input, Modal, Select, Upload } from "antd";
const { TextArea } = Input;
class EditQuestionForm extends Component {
  render() {
    const { visible, onCancel, onOk, form, confirmLoading, currentRowData } =
      this.props;
    const { getFieldDecorator } = form;
    const { id, title, description, questionType, answerType } = currentRowData;
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
        title="Edit Jurusan"
        visible={visible}
        onCancel={onCancel}
        onOk={onOk}
        confirmLoading={confirmLoading}
      >
        <Form {...formItemLayout}>
          <Form.Item label="ID Jurusan:">
            {getFieldDecorator("id", {
              initialValue: id,
            })(<Input disabled />)}
          </Form.Item>
          <Form.Item label="Pertanyaan :">
            {getFieldDecorator("title", {
              rules: [
                { required: true, message: "Silahkan isikan pertanyaan" },
              ],
              initialValue: title,
            })(<Input placeholder="Pertanyaan" />)}
          </Form.Item>
          <Form.Item label="Deskripsi Pertanyaan:">
            {getFieldDecorator("description", {
              rules: [
                {
                  required: true,
                  message: "Silahkan isikan deskripsi pertanyaan",
                },
              ],
              initialValue: description,
            })(<TextArea rows={4} placeholder="Deskripsi pertanyaan" />)}
          </Form.Item>
          <Form.Item label="Tipe Pertanyaan:">
            {getFieldDecorator("question_type", {
              rules: [
                {
                  required: true,
                  message: "Silahkan pilih tipe pertanyaan",
                },
              ],
              initialValue: questionType,
            })(
              <Select
                style={{ width: 300 }}
                placeholder="Pilih tipe pertanyaan"
              >
                <Select.Option value={"IMAGE"}>Gambar</Select.Option>
                <Select.Option value={"AUDIO"}>Musik / Audio</Select.Option>
                <Select.Option value={"VIDEO"}>Video</Select.Option>
                <Select.Option value={"NORMAL"}>Normal</Select.Option>
              </Select>
            )}
          </Form.Item>
          <Form.Item label="Tipe Jawaban:">
            {getFieldDecorator("answer_type", {
              rules: [
                {
                  required: true,
                  message: "Silahkan pilih tipe jawaban",
                },
              ],
              initialValue: answerType,
            })(
              <Select style={{ width: 300 }} placeholder="Pilih tipe jawaban">
                <Select.Option value={"MULTIPLE_CHOICE"}>
                  Pilihan Ganda
                </Select.Option>
                <Select.Option value={"BOOLEAN"}>Benar / Salah</Select.Option>
                {/* <Select.Option value={"COMPLETION"}>
                  Menyelesaikan kalimat rumpang
                </Select.Option> */}
              </Select>
            )}
          </Form.Item>
          <Form.Item label="File">
            {getFieldDecorator("file")(
              <Upload.Dragger
                name="file"
                beforeUpload={() => false}
                maxCount={1}
              >
                <p className="ant-upload-drag-icon">
                  <Icon type="inbox" />
                </p>
                <p className="ant-upload-text">Klik atau Seret file ke sini</p>
                <p className="ant-upload-hint">support semua file</p>
              </Upload.Dragger>
            )}
          </Form.Item>
        </Form>
      </Modal>
    );
  }
}

export default Form.create({ name: "EditQuestionForm" })(EditQuestionForm);
