use serde::{Deserialize, Serialize};

#[derive(Debug, Clone, Default, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct OpenCustomTabSimpleRequest {
  pub url: String,
  pub try_native_app: bool,
}