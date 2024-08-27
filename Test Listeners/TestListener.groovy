import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class TestListener {
	Boolean isNeedReset = true 
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {
		Map variables = testCaseContext.getTestCaseVariables()
		if (GlobalVariable.isJourney) {
			if(isNeedReset) {
				Mobile.callTestCase(findTestCase('Test Cases/Login Menu/Login'), null,FailureHandling.STOP_ON_FAILURE)
			}else {
				variables.put('isRunAlone',false)
			}
		}else {
			if(variables.get('isRunAlone')) {
				Mobile.callTestCase(findTestCase('Test Cases/Login Menu/Login'), null,FailureHandling.STOP_ON_FAILURE)
			}
		}
		
	}

	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) {
		if(GlobalVariable.isJourney) {
			if(testCaseContext.getTestCaseStatus() == 'PASSED') {
				isNeedReset = false
			}else {
				isNeedReset = true
			}
		}
	}

}