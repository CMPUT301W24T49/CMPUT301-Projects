/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */
"use-strict";

const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();
exports.sendNotification = functions.firestore
    .document("Events/{eventId}/Notifications/{notificationId}")
    .onWrite((change, context) => {
      const eventId = context.params.eventId;
      const notificationId = context.params.notificationId;
      const notificationRef = admin.firestore()
          .collection("Events").doc(eventId)
          .collection("Notifications").doc(notificationId);
      return notificationRef.get().then((queryResult) => {
        const message = queryResult.data().message;
        const tokensRef = admin.firestore().collection("Events")
            .doc(eventId).collection("Notification User TokenID");
        return tokensRef.get().then((tokensSnapshot) => {
          const tokens = [];
          tokensSnapshot.forEach((doc) => {
            tokens.push(doc.data().tokenId);
          });
          if (tokens.length === 0) {
            throw new Error("No tokens found.");
          }
          const payload = {
            notification: {
              title: "You have a new notification!",
              body: message,
            },
            data: {
              title: "You have a new notification!",
              body: message,
            },
          };
          return Promise.all(tokens.map((userToken) => {
            return admin.messaging().sendToDevice(userToken, payload)
                .then((response) => {
                  console.log("Successfully sent message:", response);
                  return null;
                }).catch((error) => {
                  console.log("Error sending message:", error);
                  return null;
                });
          }));
        });
      });
    });
