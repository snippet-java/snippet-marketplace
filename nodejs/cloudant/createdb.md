## Cloudant - Create Database

This code shows you how to use the node.js API to create a cloudant database. 

The code has two basic sections:

1. The main() method. This is the entry point to the code. It creates the Cloudant object and then invokes the db.create() method.

2. A set of default parameters and the code that runs this module if it is invoked by itself (as opposed to being invoked by a require() statement).

See the Node.js documentation for more information on the implications of the require.main === module statement.

### Code Walkthrough
1. This code takes a database name as input, and attempts to create the Cloudant database. The call to create database is wrapped in a JavaScript Promise to handle the asynchronous nature of the service.

### Testing Instructions
1. This should be the **FIRST** step before proceeding with other cloudant samples.

2. The code as is, automatically uses a built-in test environment. Please change the **dbname** to `person_MYNAME` (where MYNAME should be replaced by your name to make it unique) in **testparams**. 

3. To use against your own Cloudant system, please change **dbname**, **username**, and **password** in **testparams**.

### Reference
* [Cloudant Documentation](https://docs.cloudant.com/)