const http = require("http");

async function request(options) {
  return new Promise(function (resolve, reject) {
    const request = http.request(options, function handleResponse(response) {
      resolve(response);
    });

    request.on("error", function handleError(error) {
      reject(error);
    });

    request.end();
  });
}

function assert(condition, message) {
  if (!condition) {
    throw new Error(`Assertion failed: ${message}`);
  }
}

async function testHealth() {
  const response = await request({
    hostname: "localhost",
    port: 3000,
    path: "/healthcheck",
    method: "GET",
  });

  const expectedValue = 204;
  const actualValue = response.statusCode;

  assert(
    expectedValue === actualValue,
    `Health check status code does not match, expected: "${expectedValue}", actual: "${actualValue}"`
  );
}

async function testSuite() {
  await testHealth();
}

(async function main() {
  try {
    await testSuite();
  } catch (error) {
    console.error(error);
    process.exit(1);
  }

  console.log("Success");
})();
