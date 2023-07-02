use serde::de::DeserializeOwned;
use tauri::{
    plugin::{PluginApi, PluginHandle},
    AppHandle, Runtime,
};

use crate::models::*;

#[cfg(target_os = "android")]
const PLUGIN_IDENTIFIER: &str = "codes.dreaming.plugin.CustomTabsManager";

#[cfg(target_os = "ios")]
tauri::ios_plugin_binding!(init_plugin_custom-tabs-manager);

// initializes the Kotlin or Swift plugin classes
pub fn init<R: Runtime, C: DeserializeOwned>(
    _app: &AppHandle<R>,
    api: PluginApi<R, C>,
) -> crate::Result<CustomTabsManager<R>> {
    #[cfg(target_os = "android")]
        let handle = api.register_android_plugin(PLUGIN_IDENTIFIER, "CustomTabsManagerPlugin")?;
    #[cfg(target_os = "ios")]
        let handle = api.register_ios_plugin(init_plugin_custom - tabs - manager)?;
    Ok(CustomTabsManager(handle))
}

/// Access to the custom-tabs-manager APIs.
pub struct CustomTabsManager<R: Runtime>(PluginHandle<R>);

impl<R: Runtime> CustomTabsManager<R> {
    pub fn open_custom_tab_simple(&self, payload: OpenCustomTabSimpleRequest) -> crate::Result<()> {
        self
            .0
            .run_mobile_plugin("openCustomTabSimple", payload)
            .map_err(Into::into)
    }
    pub fn may_launch_url(&self, payload: MayLaunchUrlRequest) -> crate::Result<()> {
        self
            .0
            .run_mobile_plugin("mayLaunchUrl", payload)
            .map_err(Into::into)
    }
    pub fn post_message(&self, payload: PostMessageRequest) -> crate::Result<()> {
        self
            .0
            .run_mobile_plugin("postMessage", payload)
            .map_err(Into::into)
    }
}
