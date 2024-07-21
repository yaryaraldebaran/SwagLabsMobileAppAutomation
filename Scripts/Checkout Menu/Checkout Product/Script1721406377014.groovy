import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.swaglabsmobileapp.utils.Utils

import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

Utils util = new Utils()

Mobile.callTestCase(findTestCase('Test Cases/Cart Menu/Add To Cart'), [('productNames') : productNames])

int foundProducts = 0

for (def productName : productNames) {
    KeywordUtil.logInfo('product name : ' + productName)

    Mobile.scrollToText(productName)
	//there is an element remove btn that not viewed but the product name is scrolled at 
    if (Mobile.verifyElementExist(findTestObject('Object Repository/Checkout Page/btnRemoveOnCheckout', [('productName') : productName]), 
        3, FailureHandling.CONTINUE_ON_FAILURE)) {
        foundProducts++
    }else {
		//if not found, then try to swipe screen
		Mobile.swipe(0, 0, 0, 0)
	}
}

Mobile.verifyElementText(findTestObject('Object Repository/Product List/txtCart'), Integer.toString(foundProducts), FailureHandling.STOP_ON_FAILURE)

Mobile.scrollToText('CHECKOUT')

Mobile.tap(findTestObject('Object Repository/Cart Page/btnCheckout'), 0)


