import {invoke} from "@tauri-apps/api/primitives";

let greetInputEl: HTMLInputElement | null;
let greetMsgEl: HTMLElement | null;

async function open_custom_tab_simple() {
  if (greetMsgEl && greetInputEl) {
    // Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
    greetMsgEl.textContent = await invoke("open_custom_tab_simple", {
      url: greetInputEl.value,
    });
  }
}

window.addEventListener("DOMContentLoaded", () => {
  greetInputEl = document.querySelector("#greet-input");
  greetMsgEl = document.querySelector("#greet-msg");
  document.querySelector("#greet-form")?.addEventListener("submit", (e) => {
    e.preventDefault();
    open_custom_tab_simple();
  });
});
