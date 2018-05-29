var ctx = document.getElementById("myChart");
var labelList = [getNowDate()];
var dataList = [12];

function doChart(result) {
    var resultObj = JSON.parse(result);
    $("#power-content").append(resultObj.datastreams);
}

var myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: labelList,
        datasets: [{
            label: '单位:W ',
            data: dataList,
                    
            borderColor: [
                        
                'rgba(54, 162, 235, 1)'
            ],
            borderWidth: 2
        }]
    },
    options: {
        title: {
            display: true,
            text: "插板功率"
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    }
});


setInterval(function() {
    if(labelList.length <= 5) {
        labelList.push(getNowDate());
        dataList.push((new Date()).getSeconds());
    } else {
        labelList.shift();
        dataList.shift();
        labelList.push(getNowDate());
        dataList.push((new Date()).getSeconds());
    }
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labelList,
            datasets: [{
                label: '单位:W ',
                data: dataList,
                    
                borderColor: [
                        
                    'rgba(54, 162, 235, 1)'
                ],
                borderWidth: 2
            }]
        },
        options: {
            title: {
                display: true,
                text: "插板功率"
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });
    }, 5000);

function getNowDate() {
    //return (nowDate.getFullYear() + "年" + (nowDate.getMonth() + 1) + "月" + nowDate.getDate() + "日" + nowDate.getHours() + "时" + nowDate.getMinutes() + "分" + (nowDate.getSeconds() - num) + "秒");
    var nowDate = new Date();
    return (nowDate.getHours() + ":" + nowDate.getMinutes() + ":" + nowDate.getSeconds() + "");
}

function test(t) {
    $("#power-content").append("Some Text");
}