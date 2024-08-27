import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.awt.List

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.swaglabsmobileapp.utils.Utils

import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

Utils util = new Utils()
if (isRunAlone) {
	Mobile.callTestCase(findTestCase('Test Cases/Login Menu/Login'), null)
}


for (productName in productNames) {
	KeywordUtil.logInfo("product name : "+productName)
	Mobile.scrollToText(productName)
	Mobile.tap(findTestObject('Object Repository/Product List/btnAddToCart',['productName':productName]), 5)
}
int countSelectedProduct = 0


for (productName in productNames) {

	KeywordUtil.logInfo("product name : "+productName)
	Mobile.scrollToText(productName)
	
	if (Mobile.verifyElementExist(findTestObject('Object Repository/Product List/btnRemove',['productName':productName]), 10, FailureHandling.CONTINUE_ON_FAILURE)) {
		countSelectedProduct++
	}
}

//KeywordUtil.logInfo('prices :'+arrayPrice)

Mobile.verifyElementText(findTestObject('Object Repository/Product List/txtCart'), Integer.toString(countSelectedProduct), FailureHandling.STOP_ON_FAILURE)
if (isContinue) {
	Mobile.tap(findTestObject('Object Repository/Product List/btnCart'), 5)
}

