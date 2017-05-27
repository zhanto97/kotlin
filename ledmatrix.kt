import org.otfried.cs109ui.*
import java.awt.image.BufferedImage
import org.otfried.cs109.Color
import java.lang.System.currentTimeMillis

val authors = listOf(20160739, 20160875)
//Should be called without any arguments i.e. just type "kt LedmatrixKt"
//after compiling


//first part of animation: variables and two functions
var y: Int = 0
var x: Int = 0
var rounds: Int = 0
var up: Boolean = false

fun setup() {
	y = -10
	x = 23
	rounds = 0 
}

fun loop(leds: BufferedImage): Boolean {
	val g = ImageCanvas(leds)
	g.setColor(Color.RED)
	g.drawRectangle(13.0, y.toDouble(), 4.0, 11.0)

	if(up)
		y-=1
	else
		y+=1
	if(y == -9){
		up=false
		rounds+=1
	}
	if (y == 0)
		up = true
	
	g.setColor(Color.RED)
	if(x>13)
		g.drawRectangle(x.toDouble(), 10.0, 2.0, 2.0)
	else
		g.drawRectangle(x.toDouble(), 11.0, 4.0, 1.0)
	g.drawRectangle(0.0, 12.0, 32.0, 4.0)
	//g.drawRectangle(x.toDouble()-13, x.toDouble()-13, 3.0, 3.0)
	g.done()

	// after drawing the current image, update global variables
	x -= 1
	if (x < 6) {
		x = 23 // start again on right edge
	}
	return (rounds >= 4)
}


//second part of animation: variables and two functions
var x1: Int = 0
var y1: Int = 0
var y_left: Int = 0
var up_left : Boolean = false
var y_right: Int = 0
var up_right : Boolean = false
var direction : Int = 1
var changes: Int = 0

fun setup_game(){
	x1=20
	y1=10
	y_left = 4
	y_right = 10
	direction = 1
	changes=0
	up_left = false
	up_right = false
}

fun game(leds: BufferedImage): Boolean{
	val g = ImageCanvas(leds)
	
	g.setColor(Color.RED)
	g.drawRectangle(0.0, 0.0, 7.0, 16.0)
	g.setColor(Color.RED)
	g.drawRectangle(25.0, 0.0, 7.0, 16.0) //two rectangles
	
	g.setColor(Color.RED)
	g.drawRectangle(7.0, y_left.toDouble(), 1.0, 4.0) //left player
	
	g.setColor(Color.RED)
	g.drawRectangle(24.0, y_right.toDouble(), 1.0, 4.0) //right player
	
	g.setColor(Color.RED)
	g.drawRectangle(x1.toDouble(), y1.toDouble(), 1.0, 1.0) //the ball
	g.done()
	
	//Directions of the ball
	if (y1 == 15){
		direction = 2
		changes+=1
	}
	else if (x1 == 8){
		direction = 3
		changes+=1
	}
	else if (y1 == 0){
		direction = 4
		changes+=1
	}
	
	else if (x1 == 24){
		direction = 1
		changes+=1
	}
	
	//How the ball moves in particular direction
	if(direction==1){
		x1-=1
		y1+=1
	}
	else if(direction==2){
		x1-=1
		y1-=1
	}
	else if(direction==3){
		x1+=1
		y1-=1
	}
	else if(direction==4){
		x1+=1
		y1+=1
	}
	
	//Periodic up-down movement of the left player
	if (up_left)
		y_left-=1
	else
		y_left+=1
	if (y_left == 0)
		up_left = false
	else if (y_left == 12)
		up_left = true
	
	//Periodic up-down movement of the right player
	if (up_right)
		y_right-=1
	else
		y_right+=1
	if (y_right == 0)
		up_right=false
	else if (y_right == 12)
		up_right = true
	
	return (changes>=4)
}


//third part of the animation:: variables and two functions
var x_arrow: Int = 0
val arrow = "<--"
val message = "A"
var y_message: Int = 0

fun setup_arrow() {
	// text will come in from the right
	x_arrow = 32
	y_message = 0
}

fun loop_arrow(leds: BufferedImage): Boolean {
	val g = ImageCanvas(leds)
	
	g.setFont(10.0, "SansSerif")
	g.setColor(Color.RED)
	g.drawText(arrow, x_arrow.toDouble(), 14.0)
	
	g.setFont(14.0, "SansSerif")
	g.setColor(Color.RED)
	g.drawText(message, 10.0, y_message.toDouble())
	g.done()

	// after drawing the current image, update global variables
	x_arrow -= 1
	y_message += 1
	if (y_message == 16)
		return true
	return false
}

fun showLeds(leds: BufferedImage, image: BufferedImage, cell: Int) {
	val g = ImageCanvas(image)
	g.clear(Color.BLACK)
	for (x in 0 until 32) {
		for (y in 0 until 16) {
			if ((leds.getRGB(x, y) and 0xffffff) != 0)
				g.setColor(Color.RED)
			else
				g.setColor(Color(0x300000))
			g.drawCircle((x + 1.5) * cell, (y + 1.5) * cell, 0.3 * cell)
		}
	}
	g.done()
	show(image)
}

fun performTick(leds: BufferedImage, image: BufferedImage, cell: Int, speed: Int): Boolean {
	val t0 = currentTimeMillis()
	for (x in 0 until 32)
		for (y in 0 until 16)
			leds.setRGB(x, y, 0)
	val finished = loop(leds) //main function to draw 1st animation
	showLeds(leds, image, cell)
	while (true) {
		val t1 = currentTimeMillis()
		val rest = speed - (t1 - t0)
		if (rest <= 5)
			return finished
		Thread.sleep(rest)
	}
}

//We didn't have chance to learn how to pass another function as an argument.
//So, we had to rewrite the same function 3 times.

fun performTick2(leds: BufferedImage, image: BufferedImage, cell: Int, speed: Int): Boolean {
	val t0 = currentTimeMillis()   
	for (x in 0 until 32)
		for (y in 0 until 16)
			leds.setRGB(x, y, 0)
	val finished = game(leds) //function to draw 2nd animation
	showLeds(leds, image, cell)
	while (true) {
		val t1 = currentTimeMillis()
		val rest = speed - (t1 - t0)
		if (rest <= 5)
			return finished
		Thread.sleep(rest)
	}
}

fun performTick3(leds: BufferedImage, image: BufferedImage, cell: Int, speed: Int): Boolean {
	val t0 = currentTimeMillis()  
	for (x in 0 until 32)
		for (y in 0 until 16)
			leds.setRGB(x, y, 0)
	val finished = loop_arrow(leds) //function to draw 3rd animation
	showLeds(leds, image, cell)
	while (true) {
		val t1 = currentTimeMillis()
		val rest = speed - (t1 - t0)
		if (rest <= 5)
			return finished
		Thread.sleep(rest)
	}
}

fun main(args: Array<String>) {
	var cell = 32
	var speed = 300
	try {
		if (args.size in setOf(1, 2))
		  cell = args[0].toInt()
		if (args.size == 2)
		  speed = args[1].toInt()
	}
	catch (e: NumberFormatException) {
		println("Usage: kt LedmatrixKt <led-size> <speed>")
		return
	}
	println("LED Matrix project by ${authors.joinToString(" and ")}")
	setTitle("CS109 LED Matrix")

	val leds = BufferedImage(32, 16, BufferedImage.TYPE_INT_RGB)
	val image = BufferedImage(34 * cell, 18 * cell, BufferedImage.TYPE_INT_RGB)

	setup()
	var finished = false
	while (!finished)
		finished = performTick(leds, image, cell, speed)
	finished = false
	
	setup_game()
	Thread.sleep(1000)
	while(!finished)
		finished = performTick2(leds,image,cell,speed)
	finished = false
	
	setup_arrow()
	Thread.sleep(1000)
	while(!finished)
		finished = performTick3(leds,image,cell,speed)
	Thread.sleep(1000)
	close()
}
