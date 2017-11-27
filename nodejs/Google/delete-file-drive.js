var request = require('request');
var google = require('googleapis');
var googleAuth = require('google-auth-library');

var testparams = {
  "drive_oauth_client_secret": "",
  "drive_oauth_client_id": "",
  "drive_oauth_redirect_url": ["urn:ietf:wg:oauth:2.0:oob"],
  "token": {
    "refresh_token": ""
  },
  "fileId": ""
};

function main(params) {

  return new Promise(function(resolve, reject){
      var content = {
        "client_secret": params.drive_oauth_client_secret,
        "client_id": params.drive_oauth_client_id,
        "redirect_uris": params.drive_oauth_redirect_url
      }
      authorize(content, deleteFile);
      /**
       * Create an OAuth2 client with the given credentials, and then execute the
       * given callback function.
       *
       * @param {Object} credentials The authorization client credentials.
       * @param {function} callback The callback to call with the authorized client.
       */
      function authorize(credentials, callback) {
        var clientSecret = credentials.client_secret;
        var clientId = credentials.client_id;
        var redirectUrl = credentials.redirect_uris[0];
        var auth = new googleAuth();
        var oauth2Client = new auth.OAuth2(clientId, clientSecret, redirectUrl);

        oauth2Client.credentials = params.token;
        callback(oauth2Client);
      }

      function deleteFile(auth) {
        var service = google.drive('v3');
        service.files.delete({
          auth: auth,
          fileId: params.fileId
        }, function(err, response) {
          if (err) return reject({err});
          return resolve({'message': 'file deleted'});
        });
      }
  })
}

if (require.main === module)
  main(testparams)
    .then((results) => console.log(results))
    .catch((error) => console.log(error));

exports.main = main;