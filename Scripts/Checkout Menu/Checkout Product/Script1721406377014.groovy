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

Mobile.scrollToText('CHECKOUT')

util.getWhileScroll('//android.view.ViewGroup[@content-desc="test-CHECKOUT"]')

Mobile.tap(findTestObject('Object Repository/Cart Page/btnCheckout'), 0)

Mobile.setText(findTestObject('Object Repository/Checkout Page/inputFirstName'), firstName, 5)
Mobile.setText(findTestObject('Object Repository/Checkout Page/inputLastName'),lastName,5)
Mobile.setText(findTestObject('Object Repository/Checkout Page/inputZipPostalCode'), zipCode, 5)

Mobile.tap(findTestObject('Object Repository/Checkout Page/btnContinue'), 5)
if(testCaseType == 'positive') {
	
	util.getWhileScroll('//android.view.ViewGroup[@content-desc="test-FINISH"]')
	
	Mobile.scrollToText('FINISH')
	Mobile.tap(findTestObject('Object Repository/Checkout Page/btnFinish'),5)
	
	Mobile.verifyElementExist(findTestObject('Object Repository/Checkout Page/txtThankYou'), 5, FailureHandling.STOP_ON_FAILURE)
	Mobile.verifyElementExist(findTestObject('Object Repository/Checkout Page/txtCheckoutComplete'), 5, FailureHandling.STOP_ON_FAILURE)
	Mobile.tap(findTestObject('Object Repository/Checkout Page/btnBackHome'), 5, FailureHandling.STOP_ON_FAILURE)
	Mobile.verifyElementExist(findTestObject('Object Repository/Product List/txtProductPage'), 5, FailureHandling.STOP_ON_FAILURE)
}else {
	Mobile.verifyElementText(findTestObject('Object Repository/Checkout Page/txtAlert'), alertText,FailureHandling.STOP_ON_FAILURE)
}

Mobile.tap(findTestObject('Object Repository/General/btnMenu'), 3)
Mobile.tap(findTestObject('Object Repository/General/btnLogout'), 3)
