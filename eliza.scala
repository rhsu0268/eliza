import util.control.Breaks._
import scala.io.StdIn.{readLine, readInt}
import scala.util.matching.Regex

object eliza
{
	val keywords = Array("CAN YOU", "CAN I", "YOU ARE", "YOURE", "I DONT", "I FEEL", 
	"WHY DONT YOU", "WHY CANT I", "ARE YOU", "I CANT", "I AM", "IM ", "YOU ",
	"I WANT", "WHAT", "HOW", "WHO", "WHERE", "WHEN", "WHY", "NAME", "CAUSE", "SORRY",
	"DREAM", "HELLO", "HI ", "MAYBE", " NO", "YOUR", "ALWAYS", "THINK", "ALIKE", 
	"YES", "FRIEND", "COMPUTER", "CAR", "NOKEYFOUND")


	val swaps = List(
		List("ARE","AM"),
    	List("WERE", "WAS"),
    	List("YOU","I"),
    	List("YOUR", "MY"),
    	List("IVE", "YOUVE"),
    	List("IM", "YOURE"),
    	List("YOU", "ME"),
    	List("ME", "YOU"),
    	List("AM","ARE"),
    	List("WAS", "WERE"),
    	List("I","YOU"),
    	List("MY", "YOUR"),
    	List("YOUVE", "IVE"),
    	List("YOURE", "IM")

	)

	val responsesPerKeyword = Array( 
		3, 2, 4, 4, 4, 3,
       	3, 2, 3, 3, 4, 4,
       	3, 5, 9, 9, 9, 9,
    	9, 9, 2, 4, 4, 4,
    	1, 1, 5, 5, 2, 4,
    	3, 7, 3, 6, 7, 5,
    	6)

	//var responses = Array.ofDim[String](37, 9)
	val responses = List(
		List("DON'T YOU BELIEVE THAT I CAN*", "PERHAPS YOU WOULD LIKE TO BE ABLE TO*", "YOU WANT ME TO BE ABLE TO*"),
		List( "PERHAPS YOU DON'T WANT TO*", "DO YOU WANT TO BE ABLE TO*"),
		List("WHAT MAKES YOU THINK I AM*", "DOES IT PLEASE YOU TO BELIEVE I AM*", "PERHAPS YOU WOULD LIKE TO BE*", "DO YOU SOMETIMES WISH YOU WERE*"), 
		List("WHAT MAKES YOU THINK I AM*", "DOES IT PLEASE YOU TO BELIEVE I AM*", "PERHAPS YOU WOULD LIKE TO BE*", "DO YOU SOMETIMES WISH YOU WERE*"), 
		List("DON'T YOU REALLY*", "WHY DON'T YOU*", "DO YOU WISH TO BE ABLE TO*", "DOES THAT TROUBLE YOU?"),
		List("TELL ME MORE ABOUT SUCH FEELINGS.", "DO YOU OFTEN FEEL*", "DO YOU ENJOY FEELING*"),
		List("DO YOU REALLY BELIEVE I DON'T*", "PERHAPS IN GOOD TIME I WILL*", "DO YOU WANT ME TO*"), 
		List("DO YOU THINK YOU SHOULD BE ABLE TO*", "WHY CAN'T YOU*"),
		List("WHY ARE YOU INTERESTED IN WHETHER OR NOT I AM*", "WOULD YOU PREFER IF I WERE NOT*", "PERHAPS IN YOUR FANTASIES I AM*"),
		List("HOW DO YOU KNOW YOU CAN'T*", "HAVE YOU TRIED?", "PERHAPS YOU CAN NOW*"),
		List("DID YOU COME TO ME BECAUSE YOU ARE*", "HOW LONG HAVE YOU BEEN*", "DO YOU BELIEVE IT IS NORMAL TO BE*", "DO YOU ENJOY BEING*"),
		List("DID YOU COME TO ME BECAUSE YOU ARE*", "HOW LONG HAVE YOU BEEN*", "DO YOU BELIEVE IT IS NORMAL TO BE*", "DO YOU ENJOY BEING*"),
		List("WE WERE DISCUSSING YOU-- NOT ME.", "OH, I*", "YOU'RE NOT REALLY TALKING ABOUT ME, ARE YOU?"),
		List("WHAT WOULD IT MEAN TO YOU IF YOU GOT*", "WHY DO YOU WANT*", "SUPPOSE YOU SOON GOT*", "WHAT IF YOU NEVER GOT*", "I SOMETIMES ALSO WANT*"),
		List("WHY DO YOU ASK?", "DOES THAT QUESTION INTEREST YOU?", "WHAT ANSWER WOULD PLEASE YOU THE MOST?", "WHAT DO YOU THINK?", "ARE SUCH QUESTIONS ON YOUR MIND OFTEN?", "WHAT IS IT THAT YOU REALLY WANT TO KNOW?", "HAVE YOU ASKED ANYONE ELSE?", "HAVE YOU ASKED SUCH QUESTIONS BEFORE?", "WHAT ELSE COMES TO MIND WHEN YOU ASK THAT?"),
		List("WHY DO YOU ASK?", "DOES THAT QUESTION INTEREST YOU?", "WHAT ANSWER WOULD PLEASE YOU THE MOST?", "WHAT DO YOU THINK?", "ARE SUCH QUESTIONS ON YOUR MIND OFTEN?", "WHAT IS IT THAT YOU REALLY WANT TO KNOW?", "HAVE YOU ASKED ANYONE ELSE?", "HAVE YOU ASKED SUCH QUESTIONS BEFORE?", "WHAT ELSE COMES TO MIND WHEN YOU ASK THAT?"),
		List("WHY DO YOU ASK?", "DOES THAT QUESTION INTEREST YOU?", "WHAT ANSWER WOULD PLEASE YOU THE MOST?", "WHAT DO YOU THINK?", "ARE SUCH QUESTIONS ON YOUR MIND OFTEN?", "WHAT IS IT THAT YOU REALLY WANT TO KNOW?", "HAVE YOU ASKED ANYONE ELSE?", "HAVE YOU ASKED SUCH QUESTIONS BEFORE?", "WHAT ELSE COMES TO MIND WHEN YOU ASK THAT?"), 
		List("WHY DO YOU ASK?", "DOES THAT QUESTION INTEREST YOU?", "WHAT ANSWER WOULD PLEASE YOU THE MOST?", "WHAT DO YOU THINK?", "ARE SUCH QUESTIONS ON YOUR MIND OFTEN?", "WHAT IS IT THAT YOU REALLY WANT TO KNOW?", "HAVE YOU ASKED ANYONE ELSE?", "HAVE YOU ASKED SUCH QUESTIONS BEFORE?", "WHAT ELSE COMES TO MIND WHEN YOU ASK THAT?"),
		List("WHY DO YOU ASK?", "DOES THAT QUESTION INTEREST YOU?", "WHAT ANSWER WOULD PLEASE YOU THE MOST?", "WHAT DO YOU THINK?", "ARE SUCH QUESTIONS ON YOUR MIND OFTEN?", "WHAT IS IT THAT YOU REALLY WANT TO KNOW?", "HAVE YOU ASKED ANYONE ELSE?", "HAVE YOU ASKED SUCH QUESTIONS BEFORE?", "WHAT ELSE COMES TO MIND WHEN YOU ASK THAT?"),
		List("WHY DO YOU ASK?", "DOES THAT QUESTION INTEREST YOU?", "WHAT ANSWER WOULD PLEASE YOU THE MOST?", "WHAT DO YOU THINK?", "ARE SUCH QUESTIONS ON YOUR MIND OFTEN?", "WHAT IS IT THAT YOU REALLY WANT TO KNOW?", "HAVE YOU ASKED ANYONE ELSE?", "HAVE YOU ASKED SUCH QUESTIONS BEFORE?", "WHAT ELSE COMES TO MIND WHEN YOU ASK THAT?"), 
		List("NAMES DON'T INTEREST ME.", "I DON'T CARE ABOUT NAMES-- PLEASE GO ON."),
		List("DON'T ANY OTHER REASONS COME TO MIND?", "DOES THAT REASON EXPLAIN ANY THING ELSE?", "WHAT OTHER REASONS MIGHT THERE BE?"),
		List("PLEASE DON'T APOLOGIZE.", "APOLOGIES ARE NOT NECESSARY.", "WHAT FEELINGS DO YOU HAVE WHEN YOU APOLOGIZE?", "DON'T BE SO DEFENSIVE!"),
		List("WHAT DOES THAT DREAM SUGGEST TO YOU?", "DO YOU DREAM OFTEN?", "WHAT PERSONS APPEAR IN YOUR DREAMS?", "ARE YOU DISTURBED BY YOUR DREAMS?"),
		List("HOW DO YOU DO--PLEASE STATE YOUR PROBLEM."),
		List("HOW DO YOU DO--PLEASE STATE YOUR PROBLEM."),
		List("YOU DON'T SEEM QUITE CERTAIN.", "WHY THE UNCERTAIN TONE?", "CAN'T YOU BE MORE POSITIVE?", "YOU AREN'T SURE?", "DON'T YOU KNOW?"),
		List("ARE YOU SAYING NO JUST TO BE NEGATIVE?", "YOU ARE BEING A BIT NEGATIVE.", "WHY NOT?", "ARE YOU SURE?", "WHY NO?"),
		List("WHY ARE YOU CONCERNED ABOUT MY*", "WHAT ABOUT YOUR OWN*"),
		List("CAN YOU THINK OF A SPECIFIC EXAMPLE?", "WHEN?", "WHAT ARE YOU THINKING OF?", "REALLY, ALWAYS?"),
		List("DO YOU REALLY THINK SO?", "BUT YOU ARE NOT SURE YOU*", "DO YOU DOUBT YOU*"),
		List("IN WHAT WAY?", "WHAT RESEMBLANCE DO YOU SEE?", "WHAT DOES THE SIMILARITY SUGGEST TO YOU?", "WHAT OTHER CONNECTIONS DO YOU SEE?", "COULD THERE REALLY BE SOME CONNECTION?", "HOW?"),
		List("YOU SEEM QUITE POSITIVE.", "ARE YOU SURE?", "I SEE.", "I UNDERSTAND."),
		List("WHY DO YOU BRING UP THE TOPIC OF FRIENDS?", "DO YOUR FRIENDS WORRY YOU?", "DO YOUR FRIENDS PICK ON YOU?", "ARE YOU SURE YOU HAVE ANY FRIENDS?", "DO YOU IMPOSE ON YOUR FRIENDS?", "PERHAPS YOUR LOVE FOR FRIENDS WORRIES YOU?"),
		List("DO COMPUTERS WORRY YOU?", "ARE YOU TALKING ABOUT ME IN PARTICULAR?", "ARE YOU FRIGHTENED BY MACHINES?", "WHY DO YOU MENTION COMPUTERS?", "WHAT DO YOU THINK MACHINES HAVE TO DO WITH YOUR PROBLEM?", "DON'T YOU THINK COMPUTERS CAN HELP PEOPLE?", "WHAT IS IT ABOUT MACHINES THAT WORRIES YOU?"),
		List("OH, DO YOU LIKE CARS?", "MY FAVORITE CAR IS A LAMBORGINI COUNTACH. WHAT IS YOUR FAVORITE CAR?", "MY FAVORITE CAR COMPANY IS FERRARI.  WHAT IS YOURS?", "DO YOU LIKE PORSCHES?", "DO YOU LIKE PORSCHE TURBO CARRERAS?"),
		List("SAY, DO YOU HAVE ANY PSYCHOLOGICAL PROBLEMS?", "WHAT DOES THAT SUGGEST TO YOU?", "I SEE.", "I'M NOT SURE I UNDERSTAND YOU FULLY.", "COME, COME ELUCIDATE YOUR THOUGHTS.", "CAN YOU ELABORATE ON THAT?", "THAT IS QUITE INTERESTING.")
	    
		)

	val separator = " "
	def print_introduction() : Unit = 
	{
		println("*** ELIZA ***")
		println("To stop Eliza, type 'bye'")
		println("HI! I'M ELIZA. WHAT'S YOUR PROBLEM?")
	}

	def show(x: Option[String]) = x match {
      case Some(s) => s
      case None => "?"
   	}

	def main(args: Array[String])
	{
		// declare an array to keep track of the index of the reply
		val whichReply = new Array[Int](37)

		// use the first reply for each keyword match the first time the keyword is seen
		for (i <- 0 until 37)
		{
			whichReply(i) = 0
		}

		print_introduction

		var keepLooping = true
		

		breakable
		{
			while (keepLooping)
			{
				var userInput = readLine()
				userInput = userInput.toUpperCase()

				if (userInput == "BYE")
				{
					println("THANKS FOR VISITING ME! PLEASE COME AGAIN!")
					break
				}

				// break the string into tokens
				var userInputTokens = userInput.split(separator)
				var index = 0
				var location = ""
				var locationIndex = 0;

				// see if any of the keywords is contained in userInput
				var k = 0
				breakable
				{
					for (k <- 0 until 37)
					{

						var pattern = keywords(k).r
						var result = pattern findFirstIn(userInput)
						if (result != None)
						{
							// get the input from the option
							location = result.get

							// store the index of the match
							//locationIndex = userInput.indexOfSlice(location)

							var start = userInput.indexOfSlice(location)
							var length = keywords(k).length()
							locationIndex = start + length + 1

							index = k
							break
						}

					}
				}

				// index = index of keyword
				println("index: " + index)

				// location = keyword string
				println("location: " + location)

				// locationIndex = index of the keyword inside input
				println("locationIndex: " + locationIndex)


				// build eliza's response
				var baseResponse = responses(index)(whichReply(index))
				//println(baseResponse)
				
				// calculate the length of the baseResponse
				var baseLength = baseResponse.length()

				// check whether our string has a * at the end
				if (baseResponse.charAt(baseLength - 1) != '*')
				{
					var reply = baseResponse
					println("Final reply: " + reply)
				}
				else 
				{
					// if we have a *, fill in the remaining with the user input
					var reply = baseResponse.substring(0, baseResponse.length() - 1)
					println("raw reply: " + reply)
					println("Now processing...")

					// get the substring without keywords
					var newInput = userInput.substring(locationIndex, userInput.length())

					if (newInput.length() == 0)
					{
						newInput = "..."
					}
					

					println("New Input: " + newInput)
					// seperate it into tokens
					// now add in the rest of the user input
					var tokens = newInput.split(separator)

					println(tokens(0))

					for (z <- 0 until tokens.length)
					{
						println("Token z: " + tokens(z))
					}

					var token = ""

					var count = 0
					while (count < tokens.length)
					{
						token = tokens(count)
						for (j <- 0 until 14)
						{
							if (swaps(j)(0) == token)
							{
								println(token)
								token = swaps(j)(1)
								break
							}
						}

						// append a space and the token
						println("Appending space and token: " + reply)
						reply += " "
						reply += token 
						count += 1
						//println("count" + count)
					}
					reply += "?"
					println("Final reply: " + reply)
				}

				// increment the index of the reply so we have a different reply next time
				whichReply(index) += 1

				// reset the index to 0 if we hit the number of responses for that keyword
				if (whichReply(index) >= responsesPerKeyword(index))
				{
					whichReply(index) = 0
				}

				

	
			}


			
		}
	}
}