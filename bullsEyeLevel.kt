//mini android app using phone's accelerometer
//bull's eye level gauge
import org.otfried.cs109.Context
import org.otfried.cs109.MiniApp
import org.otfried.cs109.Canvas
import org.otfried.cs109.Color
import org.otfried.cs109.DrawStyle
import org.otfried.cs109.TextAlign
import java.lang.Math

fun sq(x: Double) = x * x

class Main(val ctx: Context) : MiniApp {
  var gravity = arrayOf(0.0, 0.0, 0.0)

  init {
    ctx.setTitle("Gravity sensor demo #1")
    ctx.onGravity { x, y, z -> updateGravity(x, y, z) }
  }

  fun updateGravity(x: Double, y: Double, z: Double) {
    gravity = arrayOf(x, y, z)
    ctx.update()
  }

  override fun onDraw(canvas: Canvas) {
    val x_center = canvas.width / 2.0
	val y_center = canvas.height / 2.0
	val const = 600.0 / Math.PI
	
	var (x1, y1, z1) = gravity
	var n = Math.sqrt(sq(x1) + sq(y1) + sq(z1))
	x1 = x1/n
	y1 = y1/n
	z1 = z1/n
	
	var b = Math.acos( Math.abs(z1) )
	var a = Math.atan2( -y1, x1)
	var d = b * const
	if (d > 240.0)
		d = 240.0
	var inX = d * Math.cos(a)
	var inY = d * Math.sin(a)
	
    canvas.clear(Color(255, 255, 192))
	
    canvas.setColor(Color.BLUE)
	canvas.setLineWidth(2.0)
	canvas.drawCircle(x_center, y_center, 300.0, DrawStyle.STROKE)
	
	canvas.setColor(Color.BLUE)
	canvas.setLineWidth(2.0)
	canvas.drawCircle(x_center, y_center, 80.0, DrawStyle.STROKE)
	
	canvas.setColor(Color.BLUE)
	canvas.drawCircle(x_center + inX, y_center + inY, 60.0, DrawStyle.FILL )
	
    //canvas.setFont(48.0)
    //for (i in 0..2)
      //canvas.drawText("%.3f".format(gravity[i]), x_center, 80.0 + i * 60.0, TextAlign.CENTER)
    //val norm = Math.sqrt(sq(gravity[0]) + sq(gravity[1]) + sq(gravity[2]))
    //canvas.drawText("%.3f".format(n), x_center, 300.0, TextAlign.CENTER)
  }
}
