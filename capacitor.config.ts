import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.barak.presensi',
  appName: 'Presensi PT. BARAK',
  webDir: 'dist',
  server: {
    url: 'https://script.google.com/macros/s/AKfycbyuRsms6AJluSSmaOQCjyFR7QTNiOVO2gOBsdGq0ndJ_h7RCObweu2udHwzz9FoBZF-WA/exec',
    cleartext: true,
    allowNavigation: [
      'script.google.com',
      '*.google.com',
      '*.googleusercontent.com',
      'script.googleusercontent.com',
      'cdnjs.cloudflare.com',
      '*.tile.openstreetmap.org',
    ],
  },
};

export default config;
