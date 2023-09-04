import Clipboard from "clipboard";
import { message } from "antd";

function clipboardSuccess() {
  message.success("复制 Berhasil");
}

function clipboardError() {
  message.error("复制失败");
}

export default function handleClipboard(text, event) {
  const clipboard = new Clipboard(event.target, {
    text: () => text,
  });
  clipboard.on("success", () => {
    clipboardSuccess();
    clipboard.destroy();
  });
  clipboard.on("error", () => {
    clipboardError();
    clipboard.destroy();
  });
  clipboard.onClick(event);
}
