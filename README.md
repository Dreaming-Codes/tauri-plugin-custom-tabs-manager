# Tauri Plugin – Custom Tabs Manager
This Tauri plugin allows the spawning of a Custom Tab. At the moment, the plugin exclusively supports Android. Support for iOS is planned, but currently not available. Desktop support is not available. I aim to introduce more advanced features, such as custom tab customization and interaction, in future releases.
My example application code can be found in the directory ./examples/tauri-app.

### Example Code in Rust
Here's a brief demonstration of how one can spawn a simple custom tab using our Tauri plugin:
```rust
#[tauri::command]
fn open_custom_tab_simple(app_handle: tauri::AppHandle, url: &str) {
    tauri_plugin_custom_tabs_manager::CustomTabsManagerExt::custom_tabs_manager(&app_handle).open_custom_tab_simple(OpenCustomTabSimpleRequest {
        url: url.to_string(),
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
```
### Including into your project
As of now, our plugin is not published on https://crates.io/ due to a bug in the latest alpha of Tauri that needs fixing before this plugin can work. This does not break compatibility with the official version of Tauri, however, and the plugin can be used in your app without worries by importing it from GitHub putting this into your Cargo.toml:
```toml
tauri-plugin-custom-tabs-manager = { git = "https://github.com/Dreaming-Codes/tauri-plugin-custom-tabs-manager"}
```

https://github.com/Dreaming-Codes/tauri-plugin-custom-tabs-manager/assets/10401566/28e1248b-434a-4ce7-bfc1-37847bd18b77
