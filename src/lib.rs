pub use models::*;
use tauri::{
    plugin::{Builder, TauriPlugin},
    Manager, Runtime,
};

#[cfg(desktop)]
mod desktop;
#[cfg(mobile)]
mod mobile;

mod commands;
mod error;
mod models;

pub use error::{Error, Result};

#[cfg(desktop)]
use desktop::CustomTabsManager;
#[cfg(mobile)]
use mobile::CustomTabsManager;

/// Extensions to [`tauri::App`], [`tauri::AppHandle`] and [`tauri::Window`] to access the custom-tabs-manager APIs.
pub trait CustomTabsManagerExt<R: Runtime> {
    fn custom_tabs_manager(&self) -> &CustomTabsManager<R>;
}

impl<R: Runtime, T: Manager<R>> crate::CustomTabsManagerExt<R> for T {
    fn custom_tabs_manager(&self) -> &CustomTabsManager<R> {
        self.state::<CustomTabsManager<R>>().inner()
    }
}

/// Initializes the plugin.
pub fn init<R: Runtime>() -> TauriPlugin<R> {
    Builder::new("custom-tabs-manager")
        .invoke_handler(tauri::generate_handler![
            commands::may_launch_url,
            commands::open_custom_tab_simple,
            commands::post_message
        ])
        .setup(|app, api| {
            #[cfg(mobile)]
            let custom_tabs_manager = mobile::init(app, api)?;
            #[cfg(desktop)]
            let custom_tabs_manager = desktop::init(app, api)?;
            app.manage(custom_tabs_manager);

            Ok(())
        })
        .build()
}
