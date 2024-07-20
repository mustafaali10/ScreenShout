import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

@Composable
fun AutoResizedText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    style: TextStyle
) {
    var resizedTextStyle by remember { mutableStateOf(style) }
    var shouldDraw by remember {
        mutableStateOf(false)
    }

    val defaultFontSize= 100.sp

    Text(text = text,
        fontWeight = FontWeight.Bold,
        color=color,
        modifier=modifier.drawWithContent { if(shouldDraw) {drawContent()} },
        softWrap = false,
        style=resizedTextStyle,
        onTextLayout = { result->
            if (result.didOverflowHeight||result.didOverflowWidth){
                if(style.fontSize.isUnspecified){
                    resizedTextStyle=resizedTextStyle.copy(fontSize = defaultFontSize)
                }
                resizedTextStyle=resizedTextStyle.copy(fontSize = resizedTextStyle.fontSize*0.95)
            }
            else{
                shouldDraw=true
            }

        }
    )

}
