import Loadable from "react-loadable";
import Loading from "@/components/Loading";
import Religion from "../views/religion";
import SubjectGroup from "../views/subject-group";
import Student from "../views/student";
import Subject from "../views/subject";
const Dashboard = Loadable({
  loader: () => import(/*webpackChunkName:'Dashboard'*/ "@/views/dashboard"),
  loading: Loading,
});
const Doc = Loadable({
  loader: () => import(/*webpackChunkName:'Doc'*/ "@/views/doc"),
  loading: Loading,
});
const Guide = Loadable({
  loader: () => import(/*webpackChunkName:'Guide'*/ "@/views/guide"),
  loading: Loading,
});
const Explanation = Loadable({
  loader: () => import(/*webpackChunkName:'Explanation'*/ "@/views/permission"),
  loading: Loading,
});
const AdminPage = Loadable({
  loader: () =>
    import(/*webpackChunkName:'AdminPage'*/ "@/views/permission/adminPage"),
  loading: Loading,
});
const GuestPage = Loadable({
  loader: () =>
    import(/*webpackChunkName:'GuestPage'*/ "@/views/permission/guestPage"),
  loading: Loading,
});
const EditorPage = Loadable({
  loader: () =>
    import(/*webpackChunkName:'EditorPage'*/ "@/views/permission/editorPage"),
  loading: Loading,
});
const RichTextEditor = Loadable({
  loader: () =>
    import(
      /*webpackChunkName:'RichTextEditor'*/ "@/views/components-demo/richTextEditor"
    ),
  loading: Loading,
});
const Markdown = Loadable({
  loader: () =>
    import(/*webpackChunkName:'Markdown'*/ "@/views/components-demo/Markdown"),
  loading: Loading,
});
const Draggable = Loadable({
  loader: () =>
    import(
      /*webpackChunkName:'Draggable'*/ "@/views/components-demo/draggable"
    ),
  loading: Loading,
});
const KeyboardChart = Loadable({
  loader: () =>
    import(/*webpackChunkName:'KeyboardChart'*/ "@/views/charts/keyboard"),
  loading: Loading,
});
const LineChart = Loadable({
  loader: () => import(/*webpackChunkName:'LineChart'*/ "@/views/charts/line"),
  loading: Loading,
});
const MixChart = Loadable({
  loader: () =>
    import(/*webpackChunkName:'MixChart'*/ "@/views/charts/mixChart"),
  loading: Loading,
});
const Menu1_1 = Loadable({
  loader: () =>
    import(/*webpackChunkName:'Menu1_1'*/ "@/views/nested/menu1/menu1-1"),
  loading: Loading,
});
const Menu1_2_1 = Loadable({
  loader: () =>
    import(
      /*webpackChunkName:'Menu1_2_1'*/ "@/views/nested/menu1/menu1-2/menu1-2-1"
    ),
  loading: Loading,
});
const Table = Loadable({
  loader: () => import(/*webpackChunkName:'Table'*/ "@/views/table"),
  loading: Loading,
});
const ExportExcel = Loadable({
  loader: () =>
    import(/*webpackChunkName:'ExportExcel'*/ "@/views/excel/exportExcel"),
  loading: Loading,
});
const UploadExcel = Loadable({
  loader: () =>
    import(/*webpackChunkName:'UploadExcel'*/ "@/views/excel/uploadExcel"),
  loading: Loading,
});
const Zip = Loadable({
  loader: () => import(/*webpackChunkName:'Zip'*/ "@/views/zip"),
  loading: Loading,
});
const Clipboard = Loadable({
  loader: () => import(/*webpackChunkName:'Clipboard'*/ "@/views/clipboard"),
  loading: Loading,
});
const Error404 = Loadable({
  loader: () => import(/*webpackChunkName:'Error404'*/ "@/views/error/404"),
  loading: Loading,
});
const User = Loadable({
  loader: () => import(/*webpackChunkName:'User'*/ "@/views/user"),
  loading: Loading,
});
const Question = Loadable({
  loader: () => import(/*webpackChunkName:'Question'*/ "@/views/question"),
  loading: Loading,
});
const Answer = Loadable({
  loader: () => import(/*webpackChunkName:'Answer'*/ "@/views/answer"),
  loading: Loading,
});
const Department = Loadable({
  loader: () => import(/*webpackChunkName:'Department'*/ "@/views/department"),
  loading: Loading,
});
const StudyProgram = Loadable({
  loader: () =>
    import(/*webpackChunkName:'StudyProgram'*/ "@/views/study-program"),
  loading: Loading,
});
const Lecture = Loadable({
  loader: () => import(/*webpackChunkName:'Lecture'*/ "@/views/lecture"),
  loading: Loading,
});
const RPS = Loadable({
  loader: () => import(/*webpackChunkName:'RPS'*/ "@/views/rps"),
  loading: Loading,
});
const RPSDetail = Loadable({
  loader: () => import(/*webpackChunkName:'RPS'*/ "@/views/rps-detail"),
  loading: Loading,
});
const FormLearning = Loadable({
  loader: () =>
    import(/*webpackChunkName:'FormLearning'*/ "@/views/form-learning"),
  loading: Loading,
});
const LearningMedia = Loadable({
  loader: () =>
    import(/*webpackChunkName:'LearningMedia'*/ "@/views/learning-media"),
  loading: Loading,
});
const LearningMethod = Loadable({
  loader: () =>
    import(/*webpackChunkName:'LearningMethod'*/ "@/views/learning-method"),
  loading: Loading,
});
const AssessmentCriteria = Loadable({
  loader: () =>
    import(
      /*webpackChunkName:'AssessmentCriteria'*/ "@/views/assessment-criteria"
    ),
  loading: Loading,
});
const AppraisalForm = Loadable({
  loader: () =>
    import(/*webpackChunkName:'AppraisalForm'*/ "@/views/appraisal-form"),
  loading: Loading,
});
const Exam = Loadable({
  loader: () => import(/*webpackChunkName:'Exam'*/ "@/views/exam"),
  loading: Loading,
});
const Quiz = Loadable({
  loader: () => import(/*webpackChunkName:'Quiz'*/ "@/views/quiz"),
  loading: Loading,
});
const Exercise = Loadable({
  loader: () => import(/*webpackChunkName:'Exercise'*/ "@/views/exercise"),
  loading: Loading,
});
const ResultExam = Loadable({
  loader: () => import(/*webpackChunkName:'Exam'*/ "@/views/result-exam"),
  loading: Loading,
});
const ResultQuiz = Loadable({
  loader: () => import(/*webpackChunkName:'Quiz'*/ "@/views/result-quiz"),
  loading: Loading,
});
const ResultExercise = Loadable({
  loader: () =>
    import(/*webpackChunkName:'Exercise'*/ "@/views/result-exercise"),
  loading: Loading,
});
const StudentExam = Loadable({
  loader: () => import(/*webpackChunkName:'Exam'*/ "@/views/student-exam"),
  loading: Loading,
});
const DoStudentExam = Loadable({
  loader: () => import(/*webpackChunkName:'Exam'*/ "@/views/do-student-exam"),
  loading: Loading,
});
const DoStudentExercise = Loadable({
  loader: () =>
    import(/*webpackChunkName:'Exam'*/ "@/views/do-student-exercise"),
  loading: Loading,
});
const DoStudentQuiz = Loadable({
  loader: () => import(/*webpackChunkName:'Exam'*/ "@/views/do-student-quiz"),
  loading: Loading,
});
const StudentQuiz = Loadable({
  loader: () => import(/*webpackChunkName:'Quiz'*/ "@/views/student-quiz"),
  loading: Loading,
});
const StudentExercise = Loadable({
  loader: () =>
    import(/*webpackChunkName:'Exercise'*/ "@/views/student-exercise"),
  loading: Loading,
});
const Grade = Loadable({
  loader: () => import(/*webpackChunkName:'Grade'*/ "@/views/grade"),
  loading: Loading,
});

const About = Loadable({
  loader: () => import(/*webpackChunkName:'About'*/ "@/views/about"),
  loading: Loading,
});
const Bug = Loadable({
  loader: () => import(/*webpackChunkName:'Bug'*/ "@/views/bug"),
  loading: Loading,
});

export default [
  {
    path: "/dashboard",
    component: Dashboard,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE", "ROLE_STUDENT"],
  },
  {
    path: "/doc",
    component: Doc,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE", "ROLE_STUDENT"],
  },
  {
    path: "/guide",
    component: Guide,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/permission/explanation",
    component: Explanation,
    roles: ["ROLE_ADMINISTRATOR"],
  },
  {
    path: "/permission/adminPage",
    component: AdminPage,
    roles: ["ROLE_ADMINISTRATOR"],
  },
  {
    path: "/permission/guestPage",
    component: GuestPage,
    roles: ["ROLE_STUDENT"],
  },
  {
    path: "/permission/editorPage",
    component: EditorPage,
    roles: ["ROLE_LECTURE"],
  },
  {
    path: "/components/richTextEditor",
    component: RichTextEditor,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/components/Markdown",
    component: Markdown,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/components/draggable",
    component: Draggable,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/charts/keyboard",
    component: KeyboardChart,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/charts/line",
    component: LineChart,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/charts/mix-chart",
    component: MixChart,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/nested/menu1/menu1-1",
    component: Menu1_1,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/nested/menu1/menu1-2/menu1-2-1",
    component: Menu1_2_1,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/table",
    component: Table,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/excel/export",
    component: ExportExcel,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/excel/upload",
    component: UploadExcel,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/zip",
    component: Zip,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/clipboard",
    component: Clipboard,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  { path: "/user", component: User, roles: ["ROLE_ADMINISTRATOR"] },
  { path: "/department", component: Department, roles: ["ROLE_ADMINISTRATOR"] },
  {
    path: "/study-program",
    component: StudyProgram,
    roles: ["ROLE_ADMINISTRATOR"],
  },
  { path: "/religion", component: Religion, roles: ["ROLE_ADMINISTRATOR"] },
  {
    path: "/subject-group",
    component: SubjectGroup,
    roles: ["ROLE_ADMINISTRATOR"],
  },
  { path: "/subject", component: Subject, roles: ["ROLE_ADMINISTRATOR"] },
  { path: "/lecture", component: Lecture, roles: ["ROLE_ADMINISTRATOR"] },
  { path: "/student", component: Student, roles: ["ROLE_ADMINISTRATOR"] },
  {
    path: "/rps",
    component: RPS,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
    exact: true,
  },
  {
    path: "/rps/:rpsID",
    component: RPSDetail,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
    exact: true,
  },
  {
    path: "/rps/:rpsID/:rpsDetailID",
    component: Question,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
    exact: true,
  },
  {
    path: "/rps/:rpsID/:rpsDetailID/:questionID",
    component: Answer,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/form-learning",
    component: FormLearning,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/learning-media",
    component: LearningMedia,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/learning-method",
    component: LearningMethod,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/assessment-criteria",
    component: AssessmentCriteria,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/appraisal-form",
    component: AppraisalForm,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/setting-exam",
    component: Exam,
    roles: ["ROLE_ADMINISTRATOR"],
    exact: true,
  },
  {
    path: "/setting-quiz",
    component: Quiz,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
    exact: true,
  },
  {
    path: "/setting-exercise",
    component: Exercise,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
    exact: true,
  },
  {
    path: "/exam",
    component: StudentExam,
    roles: ["ROLE_STUDENT"],
    exact: true,
  },
  {
    path: "/quiz",
    component: StudentQuiz,
    roles: ["ROLE_STUDENT"],
    exact: true,
  },
  {
    path: "/exercise",
    component: StudentExercise,
    roles: ["ROLE_STUDENT"],
    exact: true,
  },
  { path: "/exam/do/:id", component: DoStudentExam, roles: ["ROLE_STUDENT"] },
  { path: "/quiz/do/:id", component: DoStudentQuiz, roles: ["ROLE_STUDENT"] },
  {
    path: "/exercise/do/:id",
    component: DoStudentExercise,
    roles: ["ROLE_STUDENT"],
  },

  {
    path: "/setting-exam/result/:id",
    component: ResultExam,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/setting-quiz/result/:id",
    component: ResultQuiz,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },
  {
    path: "/setting-exercise/result/:id",
    component: ResultExercise,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE"],
  },

  { path: "/grade", component: Grade, roles: ["ROLE_ADMINISTRATOR"] },
  {
    path: "/about",
    component: About,
    roles: ["ROLE_ADMINISTRATOR", "ROLE_LECTURE", "ROLE_STUDENT"],
  },
  { path: "/bug", component: Bug, roles: ["ROLE_ADMINISTRATOR"] },
  { path: "/error/404", component: Error404 },
];
