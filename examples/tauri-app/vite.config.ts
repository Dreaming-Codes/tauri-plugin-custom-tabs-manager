import {defineConfig} from "vite";
import {internalIpV4} from "internal-ip";

const mobile =
    process.env.TAURI_ENV_PLATFORM === "android" ||
    process.env.TAURI_ENV_PLATFORM === "ios";

// https://vitejs.dev/config/
export default defineConfig(async () => ({
    // Vite options tailored for Tauri development and only applied in `tauri dev` or `tauri build`
    // prevent vite from obscuring rust errors
    clearScreen: false,
    // tauri expects a fixed port, fail if that port is not available
    server: {
        host: mobile ? "0.0.0.0" : false,
        port: 1420,
        hmr: mobile
            ? {
                protocol: "ws",
                host: await internalIpV4(),
                port: 1421,
            }
            : undefined,
        strictPort: true,
    },
    // to make use of `TAURI_DEBUG` and other env variables
    // https://tauri.studio/v1/api/config#buildconfig.beforedevcommand
    envPrefix: ["VITE_", "TAURI_"],
    build: {
        // Tauri supports es2021
        target: ["windows", "android"].includes(process.env.TAURI_ENV_PLATFORM) ? "chrome105" : "safari13",
        // don't minify for debug builds
        minify: !process.env.TAURI_ENV_DEBUG ? "esbuild" : false,
        // produce sourcemaps for debug builds
        sourcemap: !!process.env.TAURI_ENV_DEBUG,
    },
}));
