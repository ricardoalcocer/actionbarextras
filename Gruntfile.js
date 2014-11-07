module.exports = function(grunt) {

  grunt.registerTask('build', 'build the module', function() {

    var done = this.async();

    // runt ant to build the module
    var ant = grunt.util.spawn({
      cmd: 'ant'
    }, function(error, result, code) {
      if(error) {
        grunt.fail.warn(error, code);
      } else {
        grunt.log.ok('build successful');
      }
      done(error, result);
    });
    ant.stdout.on('data', function(data) {
      grunt.log.write(data);
    });
    ant.stderr.on('data', function(data) {
      grunt.log.error(data);
    });

  });

  grunt.registerTask('version', 'update version from package.json', function() {

    // read files
    var version = grunt.file.readJSON('package.json').version;
    var manifest = grunt.file.read('manifest');
    var readme = grunt.file.read('README.md');

    // update manifest
    grunt.file.write('manifest', manifest.replace(/^version.*$/m, "version: " + version));

    // update readme
    grunt.file.write('README.md', readme.replace(/gittio-[\d\.]{2,}-00B4CC\.svg/g, "gittio-" + version + "-00B4CC.svg"));

  });

  grunt.registerTask('default', ['build', 'version']);

};
