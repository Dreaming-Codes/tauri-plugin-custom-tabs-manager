use tauri::{AppHandle, command, Runtime, State, Window};

use crate::{CustomTabsManagerExt, MayLaunchUrlRequest, MyState, OpenCustomTabSimpleRequest, PostMessageRequest, Result};

#[command]
pub(crate) fn may_launch_url<R: Runtime>(
  app: AppHandle<R>,
  url: String,
) -> Result<()> {
  app.custom_tabs_manager().may_launch_url(MayLaunchUrlRequest {
    url
  })
}

#[command]
pub(crate) fn open_custom_tab_simple<R: Runtime>(
  app: AppHandle<R>,
  url: String,
  try_native_app: bool,
) -> Result<()> {
  app.custom_tabs_manager().open_custom_tab_simple(OpenCustomTabSimpleRequest {
    url,
    try_native_app,
  })
}

#[command]
pub(crate) fn post_message<R: Runtime>(
  app: AppHandle<R>,
  message: String,
) -> Result<()> {
  app.custom_tabs_manager().post_message(PostMessageRequest {
    message
  })
}