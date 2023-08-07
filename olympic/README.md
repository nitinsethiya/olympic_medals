# olympic

A [re-frame](https://github.com/day8/re-frame) application designed to show top 10 countries with medals.
The medals are provided by a data.json file which can be fetched from the API project.

To run the API project, go to the root directory of the project and run ```lein run```
or an uberjar can also be created and run by a java process.

A deliberate sleep time of 1 sec has been added in the API service request to show loading state.

If the API server is off, the response will be error in fetching from server.

### Environment Setup

1. Install [JDK 8 or later](https://openjdk.java.net/install/) (Java Development Kit)
2. Install [Node.js](https://nodejs.org/) (JavaScript runtime environment) which should include
   [NPM](https://docs.npmjs.com/cli/npm) or if your Node.js installation does not include NPM also install it.
4. Install [clj-kondo](https://github.com/borkdude/clj-kondo/blob/master/doc/install.md) (linter)
5. Clone this repo and open a terminal in the `olympic` project root directory
6. (Optional) Setup [lint cache](https://github.com/borkdude/clj-kondo#project-setup):
    ```sh
    clj-kondo --lint "$(npx shadow-cljs classpath)"
    ```
7. Setup
[linting in your editor](https://github.com/borkdude/clj-kondo/blob/master/doc/editor-integration.md)

### Running the App

Start a temporary local web server, build the app with the `dev` profile, and serve the app,
browser test runner and karma test runner with hot reload:

```sh
npm install
npx shadow-cljs watch app
```


