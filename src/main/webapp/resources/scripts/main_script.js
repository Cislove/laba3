const canvas = document.getElementById('chart');
let r = getR();
if(r !== 0 && r != null) {
    drawGraphic(r);
    drawPoints(canvas, getTableData(), r);
}

canvas.addEventListener("click", function (event) {
    r = getR();
    if (r) {
        handleClickOnGraphic(canvas, event, r);
    } else {
        alert("Значение R не установлено");
    }
});

function handleClickOnGraphic(canvas, event, r) {
    document.getElementById("graph-form");
    console.log(r);
    const rect = canvas.getBoundingClientRect();
    const clickX = event.clientX - rect.left;
    const clickY = event.clientY - rect.top;
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const R = (canvas.height - 50) / 2 / (5 / r);

    const X = (clickX - centerX) / R * r;
    const Y = (centerY - clickY) / R * r;
    let inp_x = document.getElementById("graph-form:graph-x");
    let inp_y = document.getElementById("graph-form:graph-y");
    let inp_r = document.getElementById("graph-form:graph-r");
    inp_x.value = X;
    inp_y.value = Y;
    inp_r.value = getR();
    document.getElementById("graph-form:graph-button").click();
    drawGraphic(r)
    drawPoints(canvas, getTableData(), r);
}


function drawGraphic(rValue) {
    const ctx = canvas.getContext('2d');
    const R = (canvas.height - 50) / 2 / (5 / rValue);

    const centerX = (canvas.width) / 2;
    const centerY = (canvas.height) / 2;

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    ctx.fillStyle = "rgba(35,103,153,0.8)";
    ctx.strokeStyle = "black";
    ctx.lineWidth = 1;

    ctx.beginPath();
    ctx.rect(centerX - R, centerY - R, R, R);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX, centerY - R);
    ctx.lineTo(centerX + R, centerY);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.arc(centerX, centerY, R / 2, Math.PI / 2, Math.PI, false);
    ctx.lineTo(centerX, centerY);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, canvas.height - 0);
    ctx.moveTo(0, centerY);
    ctx.lineTo(canvas.width - 0, centerY);
    ctx.strokeStyle = "black";
    ctx.stroke();

    ctx.font = "bold 16px Arial ";
    ctx.fillStyle = "black";
    ctx.fillText(`${rValue}`, centerX + R - 5, centerY + 15);
    ctx.fillText(`${rValue / 2}`, centerX + R / 2 - 10, centerY + 15);
    ctx.fillText(`${-rValue / 2}`, centerX - R / 2 - 20, centerY + 15);
    ctx.fillText(`${-rValue}`, centerX - R - 15, centerY + 15);
    ctx.fillText(`${rValue}`, centerX + 5, centerY - R + 5);
    ctx.fillText(`${rValue / 2}`, centerX + 5, centerY - R / 2 + 5);
    ctx.fillText(`${-rValue / 2}`, centerX + 5, centerY + R / 2 + 5);
    ctx.fillText(`${-rValue}`, centerX + 5, centerY + R + 5);
}

function drawPoints(graphic, points, rValue) {
    const centerX = graphic.width / 2;
    const centerY = graphic.height / 2;
    const R = (graphic.height - 50) / 2 / (5 / rValue);
    points.forEach(point => {
        drawPoint(point.x / rValue * R + centerX,
            -(point.y / rValue * R - centerY),
            isHit(point.x, point.y, rValue))
    })
}

function drawPoint(X, Y, isHit) {
    const canvas = document.getElementById('chart');
    const ctx = canvas.getContext('2d');

    ctx.fillStyle = (isHit) ? "green" : "red";
    ctx.beginPath();
    ctx.arc(X, Y, 5, 0, 2 * Math.PI); // радиус точки 5
    ctx.fill();
}

function getR() {
    const hiddenR = document.getElementById('main_form:hiddenR');
    return hiddenR.value === "0.0" ? null : hiddenR.value;
}

function updateHiddenField(data) {
    if (data.status === "success") {
        const r = getR();
        drawGraphic(r);
        drawPoints(canvas, getTableData(), r);
    }
}

function getTableData() {
    const table = document.querySelector("table");
    const rows = table.querySelectorAll("tr");

    const points = [];

    for (let i = 1; i < rows.length; i++) {
        const cells = rows[i].querySelectorAll("td");
        if (cells.length > 0) {
            const x = parseFloat(cells[0].innerText);
            const y = parseFloat(cells[1].innerText);
            const r = parseFloat(cells[2].innerText);

            points.push({x, y, r});
        }
    }
    return points;
}

function isHit(x, y, r) {
    if (x >= 0 && y >= 0) {
        return y <= r - x;
    }
    if (x <= 0 && y < 0) {
        return x * x + y * y <= r * r / 4;
    }
    if (x <= 0 && y >= 0) {
        return x >= -r && y <= r;
    }
    return false;
}

function highlightActiveButton(button) {
    const buttons = document.querySelectorAll('.y-button');
    buttons.forEach((btn) => btn.classList.remove('active'));

    button.classList.add('active');
}