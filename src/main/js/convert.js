fs = require('fs')
yaml = require('js-yaml')

var files = [
  "./specs/api-docs",
  "./specs/hydronics",
  "./specs/phidget"
];

files.map(function (file) {
  fs.readFile(file, "utf8", function (err, data) {
    if(err)
      console.log(err);
    else {
      var data = JSON.parse(data);
      var yml = yaml.dump(data)
      console.log(yml)

      fs.writeFile(file + ".yml", yml, function(err) {
        if(err) {
          console.log(err);
        }
      });
    }
  });
});