import Data.String
keywords = [

	"CAN YOU",
	"CAN I", 
	"YOU ARE",
	"YOU'RE",
	"I DON'T",
	"I FEEL",
	"WHY DON'T YOU",
	"WHY CAN'T I", 
	"ARE YOU",
	"I CAN'T", 
	"I AM", 
	"I'M",
	"WHEN",
	"WHY",
	"NAME",
	"CAUSE",
	"SORRY", 
	"DREAM",
	"HELLO",
	"HI",
	"MAYBE",
	" NO", 
	"YOUR", 
	"ALWAYS",
	"THINK",
	"ALIKE", 
	"YES",
	"FRIEND",
	"COMPUTER",
	"CAR", 
	"NOKEYFOUND"]

--action :: IO String
--action :: [Int] -> [Int]

addThree :: Int -> Int -> Int -> Int
addThree x y z = x + y + z

getLength :: () -> Int
getLength () = length keywords

main = do
	putStrLn "HI! I'M ELIZA. WHAT'S YOUR PROBLEM?"
	input <- getLine
	putStrLn input


	--check the string
	if input == "bye"
		then do
			putStrLn "Thanks for visiting me! Goodbye!"
		else do
			putStrLn "How do you do?"
			--show keywords
	--putStrLn result
	print (getLength ())
	putStrLn "---End---"



	


