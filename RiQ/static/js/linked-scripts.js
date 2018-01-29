function plotTimings(data){

	var riq_t  = ( data['riq']  == -1 ? 0 : data['riq']);
	var virt_t = ( data['virt'] == -1 ? 0 : data['virt']);
	var jena_t = ( data['jena'] == -1 ? 0 : data['jena']);


        riqT  = {
            name: 'RIQ',
            data: [Number(riq_t)],
            color: '#0101DF',
            stack:0
        };
        virtT =  {
            name: 'Virtuoso',
            data: [Number(virt_t)],
            color:'red',
                        stack:2

        };
        jenaT = {
            name: 'JenaTDB',
            data: [Number(jena_t)],
                        color: 'green',
            stack:1
        };

        serData = [riqT,virtT,jenaT];
        var i = 0;
        while (i<serData.length){
                if(serData[i].data==0)
                        serData.splice(i,1);
                else
                        i++;
        }




    $('#time').highcharts({
        chart: {
			type: 'column',
			margin: 0,
			marginLeft: 8,
			height: 300,
			width: 265,
			borderWidth:0,
			marginTop: 60,
			marginBottom: 80,
            backgroundColor: 'none',



        },
        title: {
            text: '',
			style: {
						fontSize: "14px",
						color:"#000"
					},
        },
        xAxis: {
			min:0,
			gridLineWidth:0,
			tickLength: 0,
			lineColor:'#3BBEC0',
			labels: {
				enabled: false
            }


        },
        yAxis: {
			gridLineWidth:0,
			min:0,
			max:300,
            labels: {
				enabled: false
            }
        },
        tooltip: {
            valueSuffix: ' seconds',
		formatter: function () {
                return '<b>'+this.series.name+': '+this.y + '</b>';
            },
            positioner:function(labelWidth, labelHeight, point){

				return {
					x: 25,
					y: 28
				};
			}
        },
        plotOptions: {

            series: {
                pointWidth: 45,
                stacking: 'normal'

            },
            column:
            	{
				stacking: 'normal',
				slicedOffset: 0,
                dataLabels: {
					enabled: true,

                                        style: {
                                                fontWeight:'bold',
                                                color: "#000000",
                                                textShadow:'none',
                                        },
					y:-20,
                			color: '#000000',
                			align: 'center',
					verticalAlign:'top',
                			format: '{point.y:.1f}', // one decimal


                }
            }
        },
        legend: {

            floating: false,
            y:10,
            borderWidth: 0,
            backgroundColor: 'none',
            shadow: false,
			itemMarginBottom:5,
			width: 190

        },
        credits: {
            enabled: false
        },
        series:serData 
  });
}


function getStatusUpdates(qId){

                var isDone='false';
                var url = "/linked/getstatus/?qid="+qId;
                isDone=httpGet(url)

                if(isDone == 'false') {
                       setTimeout(getStatusUpdates, 15000,qId); /* this checks the flag every 10 seconds*/
                } else if(isDone == 'true'){ 
                	document.getElementById('rLoader').innerHTML='';
                	document.getElementById('tLoader').innerHTML='';
			getQueryResults(qId);
			getQueryTimimgs(qId);
			
		}
		else{
                       document.getElementById('rLoader').innerHTML='';
                       document.getElementById('tLoader').innerHTML='';

		}

}
function displayLoaders(){
	document.getElementById('note').innerHTML='';
	var ldrImgResults = "<img src='/static/images/ajax-loader-green.gif' style='display: block;margin: auto; margin-top:5px;'/>";
	var ldrImgTime = "<img src='/static/images/ajax-loader-green.gif' style='display: block;margin: auto; margin-top:100px;'/>";
	var loadR = document.getElementById('rLoader');
	var loadT = document.getElementById('tLoader');
	var results = document.getElementById('results');
	var time = document.getElementById('time');
	loadR.innerHTML=ldrImgResults;
	loadT.innerHTML=ldrImgTime;
	results.innerHTML="";
	time.innerHTML="";


}
function getQueryTimimgs(qId)
{	
	$.ajax({
	url: "/linked/gettimings/?qid="+qId,
	type: "GET",
	dataType: "json",
		error: function(response,n, textStatus, exception) {
		alert('Form Error: ' + response.responseText);
		console.log(n);
		console.log(textStatus);

	},
	success: function(data) {
		plotTimings(data);
		if(qId != 'CUSTOM')
                        document.getElementById('note').innerHTML="Note: displaying previously run <br />timings for RIQ's competitors.";
		else
                        document.getElementById('note').innerHTML="Note: Time spent by selected tool(s) <br />at the endpoint";


		}});

}


function getQueryResults(qId)
{

 	$.ajax({
 	url: "/linked/getresults?qid="+qId,
 	type: "GET",
 	dataType: "text",
 		error: function(response,n, textStatus, exception) {
 		alert('Form Error: ' + response.responseText);
 		console.log(n);
 		console.log(textStatus);

 	},
 	success: function(data) {

                        var rframe = document.getElementById('results');
                        var xmlDoc = parseXML(data);
                        rframe.innerHTML= ConvertToTable(xmlDoc);
                        xmlDoc = null;

 		}});


}

function showQuery(e)
{
	var d= "name=" + e.value;
	var obj = $('#query').unbind();

	$.ajax({
	url: "/linked/getquery/",
	type: "GET",
	dataType: "text",
	data: d,
	success: function(m) {
		obj.text(m);
		obj.html(obj.html().replace(/\n/g,'&nbsp;<br/>'));
		obj.html(obj.html().replace(/\t/g,'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'));
		obj.bind('DOMNodeInserted DOMSubtreeModified DOMNodeRemoved', function(event) {
		document.getElementById("queryDisplay").selectedIndex = 3;

		});
	}
	});
}

//Javascript to run RIQ
function runRIQ(e)
{
	var qId = 'CUSTOM';
        //get query id
        var e = document.getElementById("queryDisplay");
        if ( e.selectedIndex != -1)
		qId = e.options[e.selectedIndex].value;
	
	document.getElementById("query-text").value= $('#query').html().replace(/<br\s*[\/]?>|&nbsp;/gi,' ').replace(/&lt;/gi,' <').replace(/&gt;/gi,'> ');

        var format = document.getElementById("outputformat");
        format.options[3].selected = true;
        //alert(format);

	var form = document.getElementById("frmRIQ");
	var formURL = form.action;
	var postData = $('#frmRIQ').serialize();
	    	
	displayLoaders();
	$.ajax({
	url: '/linked/',
	type: "POST",
	dataType: "text",
	data: postData,
	timeout: 9000000000,
	success: function(response,n, textStatus, exception) {

		console.log("Linked Form Submitted Successfully");
		getStatusUpdates(qId);
	},
	error: function(response,n, textStatus, exception) {
		alert('Form Error: ' + response.responseText);
		console.log(n);
		console.log(textStatus);

		document.getElementById('note').innerHTML='';
        	loadR.innerHTML='';
        	loadT.innerHTML='';
        	results.innerHTML="";
        	time.innerHTML="";

	}
	});
	
	//displayLoaders();
	//getQueryResults(qId);

	//getQueryTimimgs(qId);

}

