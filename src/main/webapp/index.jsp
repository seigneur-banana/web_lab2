<%@ page import="other.Point" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="hb" class="other.HistoryBean" scope="session"/>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="utf-8"/>
    <title>lab1</title>
    <link rel="stylesheet" href="css/styles.css" type="text/css"/>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"
            integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <style>
        @font-face {
            font-family: fantasy;
            src: url(fonts/impact.ttf) format("truetype");
            font-style: normal;
            font-weight: normal;
        }
        #mainHeading {
            width: 756px;
            text-align: center;
            margin-left: auto;
            margin-right: auto;
            color: #ffd35f;
            font-family: "Times New Roman", sans-serif;
            font-style: italic;
            font-size: 23pt;
        }
        #mainHeading::selection {
            color: white;
            background-color: #114da4;
        }
        #mainHeading span {
            color: #66ff00;
            font-size: 23pt;
        }
        #mainHeading span::selection {
            color: white;
            background-color: #4a98ff;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <th colspan=2 id="mainHeading"><span>Бадмаев Николай Игоревич</span> гр. P3230 вариант: 30004</th>
    </tr>
    <form action="controller" method="get" id="pointCheckForm">
        <tr>
            <td><b>Изменение Х:</b>
                <label>
                    <select class="xSelect" name="coordinateX" onchange="drawArea(this.value)">
                        <%
                            for (double i = -2; i <= 2; i += 0.5) {
                                out.println("<option" + (i == -2 ? " selected " : " ") + "value=" + "\"" + i + "\">" + i + "</option>");
                            }
                        %>
                    </select>
                </label>
            </td>
            <td rowspan="4"> title="График" alt="График"/><canvas width="264" height="264" id="canvas"></canvas></td>
        </tr>
        <tr>
            <td><b>Изменение Y:</b><div class="text"><label data-validate="Обязательное поле"><input type="text" required title="Число из промежутка (-3...3); разделитель целой и дробной части - точка (,)" class="number" name="coordinateY" data-min="-3" data-max="3" data-separator="," autocomplete="off" autofocus></label></div></td>
        </tr>
        <tr>
            <td><b>Изменение R:</b>
                <div class="form_radio_btn">
                    <%
                        for (int i = 1; i <= 5; i++) {
                            out.println("<input id=\"radio-"+i+"\" "+ (i == 1 ? " checked " : " ") +"name=\"raduis\" value=" + "\"" + i + "\">"+" type=\"radio\" onclick=\"setR("+i+")\">" + i + "</input>");
                            out.println("<label for=\"radio-"+i+"\">1</label>");
                        }
                    %>
                </div>

            </td>
        </tr>
        <tr>
            <td><button class="submitButton" type="submit">Вычислить</button></td>
        </tr>
    </form>
    <tr>
        <td colspan=2>
            <%
                if (hb.getHistory().isEmpty()) {
                    out.println("<div class='error'>История запросов пуста</div>");
                } else {
                    out.println("<table class='history'>");
                    out.println("<thead><tr><th>Значение X</th><th>Значение Y</th><th>Значение R</th><th>Попадание</th><th>Дата и время</th></tr></thead>");
                    out.println("<tbody>");
                    for (Point p : hb.getHistory()) {
                        out.println("<tr><td>" + p.getX() + "</td><td>" + p.getY() + "</td><td>" + p.getR() + "</td><td>" + (p.isInArea() ? "Да" : "Нет") + "</td><td>" + (new SimpleDateFormat("dd.MM.yy HH:mm:ss")).format(p.getDate()) + "</td></tr>");
                    }
                    out.println("</tbody>");
                    out.println("</table>");
                }
            %>
        </td>
    </tr>
</table>
</body>
</html>

<script>
    function onlyDigits() {
        const separator = this.dataset.separator;
        const replaced = new RegExp('[^\\d\\' + separator + '\\-]', "g");
        const regex = new RegExp('\\' + separator, "g");
        this.value = this.value.replace(replaced, "");

        const minValue = parseFloat(this.dataset.min);
        const maxValue = parseFloat(this.dataset.max);
        const val = parseFloat(separator === "." ? this.value : this.value.replace(new RegExp(separator, "g"), "."));
        if (minValue <= maxValue) {
            if (this.value[0] === "-") {
                if (this.value.length > 8)
                    this.value = this.value.substr(0, 8);
            } else {
                if (this.value.length > 7)
                    this.value = this.value.substr(0, 7);
            }

            if (minValue < 0 && maxValue < 0) {
                if (this.value[0] !== "-")
                    this.value = "-" + this.value[0];
            } else if (minValue >= 0 && maxValue >= 0) {
                if (this.value[0] === "-")
                    this.value = this.value.substr(0, 0);
            }

            if (val < minValue || val > maxValue)
                this.value = this.value.substr(0, 0);

            if (this.value.match(regex))
                if (this.value.match(regex).length > 1)
                    this.value = this.value.substr(0, 0);

            if (this.value.match(/-/g))
                if (this.value.match(/-/g).length > 1)
                    this.value = this.value.substr(0, 0);
        }
    }

    document.querySelector(".number").onkeyup = onlyDigits;

        function setX(value) {
        document.getElementById('hiddenField').value = value;
    }
        window.addEventListener('scroll', function() {
        document.getElementById('scrollUpButton').hidden = scrollY < 30;
    });
        let canvas = document.getElementById('canvas');
        let context = canvas.getContext('2d');
        canvas.addEventListener('mouseup', function (e) {
        let x = e.pageX - e.target.offsetLeft;
        let y = e.pageY - e.target.offsetTop;
        document.getElementById("hiddenField").value = ((x - 133)/36+0.01).toFixed(1);
        document.getElementsByName("coordinateY")[0].value = ((y - 133)/-36+0.005).toFixed(1);
        document.getElementById("pointCheckForm").submit();
    });
        function drawArea(r) {
        context.clearRect(0, 0, canvas.width, canvas.height);
        context.fillStyle = "black";
        context.lineWidth = 1;
        context.beginPath(); // оси
        context.moveTo(132.5, 0);
        context.lineTo(132.5, 264);
        context.moveTo(128, 6);
        context.lineTo(132.5, 0);
        context.moveTo(137, 6);
        context.lineTo(132.5, 0);
        context.moveTo(0, 132.5);
        context.lineTo(264, 132.5);
        context.moveTo(258, 137);
        context.lineTo(264, 132.5);
        context.moveTo(258, 128);
        context.lineTo(264, 132.5);
        context.stroke();
        context.font = "11pt Calibri";
        context.fillText("Y", 120, 11);
        context.fillText("X", 255, 148);
        context.fillStyle = "#4a98ff";
        context.fillRect(132.5-r*18-1.5, 133, r*18+1, r*18*2+1); // прямоугольник
        context.beginPath(); // треугольник
        context.moveTo(133, 132);
        context.lineTo(133+r*18+1.5, 132);
        context.lineTo(133, 132-r*18);
        context.lineTo(133, 132);
        context.fill();
        context.beginPath(); // круг
        context.arc(131.5, 132, r*18, -Math.PI/2, -Math.PI, true);
        context.lineTo(132, 132);
        context.lineTo(132, 132-r*18);
        context.fill();
        for(let i = 1; i <= 6; i++) {
        context.beginPath();
        context.moveTo(132.5+i*18+1, 130); // насечки по x вправо
        context.lineTo(132.5+i*18+1, 135);
        context.moveTo(132.5-i*18-1, 130); // насечки по x влево
        context.lineTo(132.5-i*18-1, 135);
        context.moveTo(130, 132.5+i*18+1); // насечки по y вниз
        context.lineTo(135, 132.5+i*18+1);
        context.moveTo(130, 132.5-i*18-1); // насечки по y вверх
        context.lineTo(135, 132.5-i*18-1);
        context.stroke();
    }
        context.fillStyle = "black";
        context.fillText("R", 120, 132.5-r*18*2+2); // R по y
        if (r !== "1.0") {
        context.fillText("0.5R", 120-19, 132.5-r*18+2);
        context.fillText("0.5R", 120-19, 132.5+r*18+4);
    }
        context.fillText("R", 120, 132.5+r*18*2+4);
        context.fillText("R", 132.5-r*18*2-5, 148); // R по x
        if (r !== "1.0") {
        context.fillText("0.5R", 132.5 - r * 18 - 10, 148);
        context.fillText("0.5R", 132.5 + r * 18 - 8, 148);
    }
        context.fillText("R", 132.5 + r * 18 * 2 - 3, 148);
        drawPointsFromHistory(); // заполняем точки из истории
    }
        function drawPointsFromHistory(){
        <%
            out.println("context.fillStyle = \"#FF0000\";");
            for (Point p : hb.getHistory()) {
                out.println("context.fillRect("+(p.getX()*36+133-1.5)+", "+(p.getY()*-36+133-2)+", 2, 2);");
            }
        %>
    }
        drawArea(3);
</script>