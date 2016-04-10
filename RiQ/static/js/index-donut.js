/*
Red 		#E87352
turquoise	#3BBEC0
yellow		#DCB543
green 		#43C487
*/
function showdonut(timedata,timepercentage){
	// Make monochrome colors and set them as default for all pies
//     Highcharts.getOptions().plotOptions.pie.colors = (function () {
//         var colors = [],
//             base = '#E87352',
//             i;
//
//         for (i = 0; i < 10; i += 1) {
//             // Start out with a darkened base color (negative brighten), and end
//             // up with a much brighter color
//             colors.push(Highcharts.Color(base).brighten((i - 3) / 7).get());
//         }
//         return colors;
//     }());
   // var colors = Highcharts.getOptions().colors;
	Highcharts.getOptions().plotOptions.pie.colors = ['#3BBEC0','#DCB543','#43C487'];
	//Highcharts.getOptions().plotOptions.pie.colors = ['#0101DF','#01A9DB','green'];
    $('#donutbox').highcharts({
		labels: {
			items : [{
				html : "Total:",
				style : {
					left : '370px',
					top : '180px',
					fontSize : '20px',
					color:'#000'
				}
			},{
				html : timedata['TOTAL'].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'s',
				style : {
					left : '340px',
					top : '200px',
					fontSize : '20px',
					color:'#000'
				}
			}]
		},
		credits: {
			  enabled: false
		  },
        chart: {
			backgroundColor:'rgba(255, 255, 255, 0.1)',
            plotBorderWidth: 0,
            plotShadow: false,
            margin:15

        },
        title: {
            text: '',
            align: 'center',
            verticalAlign: 'middle',
            y: 50
        },
        tooltip: {
            pointFormat: '<b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
				size:'200%',
				dataLabels: {
                    connectorWidth: 1,
					enabled: true,
					formatter: function() {
						return '<b>'+ this.point.name +'</b>: <br/>'+ Number(timedata[this.point.name]).toFixed(0).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'s';
                    },



//                                 dataLabels: {
//
//                     distance: -50,
//                     connectorWidth: 1,
//                     connectorColor:'#000',
//                     style: {
//                         fontWeight: 'bold',
//                         color: 'white',
//                         textShadow: 'none'
//                     },

                },

                startAngle: -90,
                endAngle: 90,
                center: ['50%', '100%']
            }
        },
        series: [{
            type: 'pie',
            name: 'Time Percentage',
            innerSize: '45%',
            data: timepercentage,
        }]
    });
}