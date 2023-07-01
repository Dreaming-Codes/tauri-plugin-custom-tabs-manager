use tauri_plugin_custom_tabs_manager::OpenCustomTabSimpleRequest;

// Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
#[tauri::command]
fn open_custom_tab_simple(app_handle: tauri::AppHandle, url: &str) {
    tauri_plugin_custom_tabs_manager::CustomTabsManagerExt::custom_tabs_manager(&app_handle).open_custom_tab_simple(OpenCustomTabSimpleRequest {
        url: url.to_string(),
        try_native_app: true,
    }).expect("error while opening custom tab")
}

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .plugin(tauri_plugin_custom_tabs_manager::init())
        .invoke_handler(tauri::generate_handler![open_custom_tab_simple])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
