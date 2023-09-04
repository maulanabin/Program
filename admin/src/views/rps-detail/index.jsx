import React, { Component } from "react";
import { Card, Button, Table, message, Divider } from "antd";
import {
  getRPSDetail,
  deleteRPSDetail,
  editRPSDetail,
  addRPSDetail,
} from "@/api/rpsDetail";
import { getFormLearnings } from "@/api/formLearning";
import { getLearningMethods } from "@/api/learningMethod";
import { getAssessmentCriterias } from "@/api/assessmentCriteria";
import { getAppraisalForms } from "@/api/appraisalForm";
import { Link } from "react-router-dom";
import TypingCard from "@/components/TypingCard";
import EditRPSDetailForm from "./forms/edit-rpsDetail-form";
import AddRPSDetailForm from "./forms/add-rpsDetail-form";
import { withRouter } from "react-router";
const { Column } = Table;

class RPSDetailDetail extends Component {
  state = {
    rpsDetail: [],
    formLearnings: [],
    learningMethods: [],
    assessmentCriterias: [],
    appraisalForms: [],
    editRPSDetailModalVisible: false,
    editRPSDetailModalLoading: false,
    currentRowData: {},
    addRPSDetailModalVisible: false,
    addRPSDetailModalLoading: false,
    rpsID: "",
  };

  getRPSDetail = async (rpsID) => {
    const result = await getRPSDetail(rpsID);
    const { content, statusCode } = result.data;
    console.log(result.data);
    if (statusCode === 200) {
      this.setState({
        rpsDetail: content,
      });
    }
  };

  getFormLearnings = async () => {
    const result = await getFormLearnings();
    const { content, statusCode } = result.data;

    if (statusCode === 200) {
      this.setState({
        formLearnings: content,
      });
    }
  };

  getLearningMethods = async () => {
    const result = await getLearningMethods();
    const { content, statusCode } = result.data;

    if (statusCode === 200) {
      this.setState({
        learningMethods: content,
      });
    }
  };

  getAssessmentCriterias = async () => {
    const result = await getAssessmentCriterias();
    const { content, statusCode } = result.data;

    if (statusCode === 200) {
      this.setState({
        assessmentCriterias: content,
      });
    }
  };

  getAppraisalForms = async () => {
    const result = await getAppraisalForms();
    const { content, statusCode } = result.data;

    if (statusCode === 200) {
      this.setState({
        appraisalForms: content,
      });
    }
  };

  handleEditRPSDetail = (row) => {
    this.setState({
      currentRowData: Object.assign({}, row),
      editRPSDetailModalVisible: true,
    });
  };

  handleDeleteRPSDetail = (row) => {
    const { id } = row;
    if (id === "admin") {
      message.error("不能Hapus Data管理员用户！");
      return;
    }
    deleteRPSDetail({ id }).then((res) => {
      message.success("Hapus Data Berhasil");
      this.getRPSDetail(this.state.rpsID);
    });
  };

  handleEditRPSDetailOk = (_) => {
    const { form } = this.editRPSDetailFormRef.props;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      this.setState({ editModalLoading: true });
      editRPSDetail(values)
        .then((response) => {
          form.resetFields();
          this.setState({
            editRPSDetailModalVisible: false,
            editRPSDetailModalLoading: false,
          });
          message.success("Berhasil Memperbarui Data!");
          this.getRPSDetail(this.state.rpsID);
        })
        .catch((e) => {
          message.success("Pengeditan gagal, harap coba lagi!");
        });
    });
  };

  handleCancel = (_) => {
    this.setState({
      editRPSDetailModalVisible: false,
      addRPSDetailModalVisible: false,
    });
  };

  handleAddRPSDetail = (row) => {
    this.setState({
      addRPSDetailModalVisible: true,
    });
  };

  handleAddRPSDetailOk = (_) => {
    const { form } = this.addRPSDetailFormRef.props;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      this.setState({ addRPSDetailModalLoading: true });
      const mergedObj = { ...{ rps_id: this.state.rpsID }, ...values };
      addRPSDetail(mergedObj)
        .then((response) => {
          form.resetFields();
          this.setState({
            addRPSDetailModalVisible: false,
            addRPSDetailModalLoading: false,
          });
          message.success("Berhasil Menambahkan Data!");
          this.getRPSDetail(this.state.rpsID);
        })
        .catch((e) => {
          message.success("添加失败,请重试!");
        });
    });
  };
  componentDidMount() {
    this.setState({
      rpsID: this.props.match.params.rpsID,
    });
    this.getRPSDetail(this.props.match.params.rpsID);
    this.getFormLearnings();
    this.getLearningMethods();
    this.getAssessmentCriterias();
    this.getAppraisalForms();
  }
  render() {
    const {
      rpsDetail,
      formLearnings,
      learningMethods,
      assessmentCriterias,
      appraisalForms,
      rpsID,
    } = this.state;
    const title = (
      <span>
        <Button type="primary" onClick={this.handleAddRPSDetail}>
          Tambahkan Detail RPSDetail
        </Button>
      </span>
    );
    const cardContent = `Di sini, Anda dapat mengelola RPSDetail sesuai dengan mata kuliah yang diampu. Di bawah ini dapat menampilkan list RPSDetail yang ada.`;
    return (
      <div className="app-container">
        <TypingCard title="Manajemen RPSDetail" source={cardContent} />
        <br />
        <Card title={title}>
          <Table bordered rowKey="id" dataSource={rpsDetail} pagination={false}>
            <Column
              title="ID RPSDetail"
              dataIndex="id"
              key="id"
              align="center"
            />
            <Column
              title="Minggu Ke"
              dataIndex="week"
              key="week"
              align="center"
            />
            <Column
              title="Sub CP Mk"
              dataIndex="sub_cp_mk"
              key="sub_cp_mk"
              align="center"
            />
            <Column
              title="Bobot"
              dataIndex="weight"
              key="weight"
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
                    onClick={this.handleEditRPSDetail.bind(null, row)}
                  />
                  <Divider type="vertical" />
                  <Link to={`/rps/${rpsID}/${row.id}`}>
                    <Button
                      type="primary"
                      shape="circle"
                      icon="diff"
                      title="Hapus Data"
                    />
                  </Link>
                  <Divider type="vertical" />
                  <Button
                    type="primary"
                    shape="circle"
                    icon="delete"
                    title="Hapus Data"
                    onClick={this.handleDeleteRPSDetail.bind(null, row)}
                  />
                </span>
              )}
            />
          </Table>
        </Card>
        <EditRPSDetailForm
          currentRowData={this.state.currentRowData}
          wrappedComponentRef={(formRef) =>
            (this.editRPSDetailFormRef = formRef)
          }
          visible={this.state.editRPSDetailModalVisible}
          confirmLoading={this.state.editRPSDetailModalLoading}
          onCancel={this.handleCancel}
          onOk={this.handleEditRPSDetailOk}
          formLearnings={formLearnings}
          learningMethods={learningMethods}
          assessmentCriterias={assessmentCriterias}
          appraisalForms={appraisalForms}
          rpsID={rpsID}
        />
        <AddRPSDetailForm
          wrappedComponentRef={(formRef) =>
            (this.addRPSDetailFormRef = formRef)
          }
          visible={this.state.addRPSDetailModalVisible}
          confirmLoading={this.state.addRPSDetailModalLoading}
          onCancel={this.handleCancel}
          onOk={this.handleAddRPSDetailOk}
          formLearnings={formLearnings}
          learningMethods={learningMethods}
          assessmentCriterias={assessmentCriterias}
          appraisalForms={appraisalForms}
          rpsID={rpsID}
        />
      </div>
    );
  }
}

export default withRouter(RPSDetailDetail);
