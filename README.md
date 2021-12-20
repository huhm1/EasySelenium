
# EasySelenium
This is a Selenium test platform. It based on Page Object Model, combine with TestNG, ExtentReports and OpenCsv.

It will help people to easily create Selenium test poject or web crawler. It will generate HTML, CVS or other test report.

# How to use it?

 1. Direct use it.
Add page objects under test.pageObjects package.
Add test cases under  test.cases package.
Add test data csv files under Data folder.
Add data models under  models package.
	
 2. Use it as a dependent project. 
if you have multiple test projects. set EasySelenium as a base test project. 
Each project in Build Path set EasySelenium as a "Required projects on the build path".
Each project also can override the Config.java and create it own.
