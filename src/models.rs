use serde::{Deserialize, Serialize};
use tauri::api::ipc::Channel;

#[derive(Debug, Clone, Default, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct OpenCustomTabSimpleRequest {
  pub url: String,
  pub try_native_app: bool,
}

#[derive(Debug, Clone, Default, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct MayLaunchUrlRequest {
  pub url: String
}

#[derive(Debug, Clone, Default, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct PostMessageRequest {
  pub message: String
}

#[derive(Clone, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct RegisterListenerRequest {
  pub event: String,
  pub channel: Channel
}