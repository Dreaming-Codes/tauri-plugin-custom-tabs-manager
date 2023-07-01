use serde::de::DeserializeOwned;
use tauri::{plugin::PluginApi, AppHandle, Runtime};

use crate::models::*;

pub fn init<R: Runtime, C: DeserializeOwned>(
  app: &AppHandle<R>,
  _api: PluginApi<R, C>,
) -> crate::Result<CustomTabsManager<R>> {
  Ok(CustomTabsManager(app.clone()))
}

/// Access to the custom-tabs-manager APIs.
pub struct CustomTabsManager<R: Runtime>(AppHandle<R>);

impl<R: Runtime> CustomTabsManager<R> {
  pub fn ping(&self, payload: PingRequest) -> crate::Result<PingResponse> {
    Ok(PingResponse {
      value: payload.value,
    })
  }
}
