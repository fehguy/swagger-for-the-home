var sw = require("../Common/node/swagger.js");
var param = require("../Common/node/paramTypes.js");
var url = require("url");
var swe = sw.errors;

/* add model includes */

function writeResponse (response, data) {
  response.header('Access-Control-Allow-Origin', "*");
  response.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
  response.header("Access-Control-Allow-Headers", "Content-Type");
  response.header("Content-Type", "application/json; charset=utf-8");
  response.send(JSON.stringify(data));
}

exports.models = models = require("../models.js");

exports.getZones = {
  'spec': {
    "description" : "Operations about pets",
    "path" : "/hydronics.{format}/zones",
    "notes" : "Returns an array of zones",
    "summary" : "Get all zones",
    "method": "GET",
    "params" : [].concat([]).concat([]).concat([]),
    "responseClass" : "List[Zone]",
    "errorResponses" : [swe.invalid('id'), swe.notFound('List[Zone]')],
    "nickname" : "getZones"
  },
  'action': function (req,res) {
    writeResponse(res, {message: "how about implementing getZones as a GET method?"});    
  }
};

