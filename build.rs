const COMMANDS: &[&str] = &["may_launch_url", "open_custom_tab_simple", "post_message"];

fn main() {
    tauri_plugin::Builder::new(COMMANDS)
        .android_path("android")
        .ios_path("ios")
        .build();
}
