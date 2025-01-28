

module.exports = {
  content: [
    './src/main/resources/templates/**/*.html', // Thymeleaf templates
    './src/main/resources/static/js/**/*.js', // JavaScript files
    './src/main/resources/static/css/**/*.css', // Corrected CSS files path
  ],
  theme: {
    extend: {},
  },
  plugins: [],
  darkMode: "class", // Use "class" for toggling dark mode manually
};

