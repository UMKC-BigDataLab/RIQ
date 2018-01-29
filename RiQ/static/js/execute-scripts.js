function plotTimings(data){

	var cache = data['type'];
	cache = cache.charAt(0).toUpperCase() + cache.slice(1);
	var riq_t = data['riq'];
	var riq_tf = data['riqf'];
	var riq = Number(riq_t) + Number (riq_tf)
	var virt_t = Number(data['virt']).toFixed(1);
	var jena_t = Number(data['jena']).toFixed(1);
	var rf_t = Number(data['rf']).toFixed(1);
	riqT  = {
            name: 'RIQ',
            data: [Number(riq.toFixed(1))],
            color: '#0101DF',
            stack:0
        };
	/*riqfT =  {
            name: 'RIQ (filtering)',
            data: [Number(riq_tf)],
	    color: '#01A9DB',
            stack:0
        };*/
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

	rfT ={
            name: 'RF',
            data: [Number(rf_t)],
            color:'#EB9100',
	    stack:3

        };
	serData = [riqT];
        if (Number(virt_t) > 0){
		serData.push(virtT);
	} 
	if (Number(jena_t) > 0){
		serData.push(jenaT);
	} 
	serData.push(rfT);	
	
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
			marginLeft: 65,
			marginRight: 40,
			height: 300,
			width: 300,
			borderWidth:0,
			marginTop: 60,
			marginBottom: 80,
            backgroundColor: 'none',



        },
        title: {
            text: cache+" Cache <br> RIQ's Filtering Time: "+data["riqf"]+"s",
	    style: {
						fontSize: "14px",
						color:"#000"
	    },
	    align: 'center',
	    x:-14,
        },
        xAxis: {
			min:0,
			gridLineWidth:0,
			tickLength: 0,
			lineColor:'#DCB543',
			labels: {
				enabled: false
            }


        },
        yAxis: {
  		title: {
                	text: 'Time (seconds)'
           	 },
		type: 'logarithmic',
            	//minorTickInterval: 0.1,
		/*
		gridLineWidth:0,
			min:0,
			max:15500,
		
            labels: {
				enabled: true
            }
*/
        },
        tooltip: {
            valueSuffix: ' seconds',
			formatter: function () {
                return '<b>' + this.y + '</b>';
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
                pointWidth: 30,
                stacking: 'normal'

            },
            column:{
			stacking: 'normal',
			//slicedOffset: 0,
                	dataLabels: {
					verticalAlign: 'center',
					enabled: true,
                     			y: -20,
					allowOverlap: true,
					style: {
						fontWeight:'bold',
						color: "#000000",
						textShadow:'none',
			    		},


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
        series: serData
    });


}
function getQueryTimimgs(args)
{

	$.ajax({
	url: "/execute/timings/"+args,
	type: "GET",
	dataType: "json",
		error: function(response,n, textStatus, exception) {
		alert('Error: ' + response.responseText);
		console.log(n);
		console.log(textStatus);

	},
	success: function(data) {
		plotTimings(data);

                var e = document.getElementById("queryDisplay");
                var qId = e.options[e.selectedIndex].value;
                //document.getElementById('filter_time').innerHTML="RIQ's Filtering Time: "+data['riqf']+'s';

                if(qId != 'CUSTOM'){
                        document.getElementById('note').innerHTML="Note: displaying previously run <br />timings for RIQ's competitors.";
		 }
		 

		}});


}

function getLable(str){
                var objValue = str;

				if (str.indexOf("http://") != -1)
				{
					objValue = objValue.substring(str.lastIndexOf("/")+1);
					objValue = objValue.replace(">", "");
				}
                return objValue.trim();
}

function getQueryGraph(args)
{

	$.ajax({
	url: "/execute/graph/"+args,
	type: "GET",
	dataType: "json",
		error: function(response,n, textStatus, exception) {
		alert('Error: ' + response.responseText);
		console.log(n);
		console.log(textStatus);

	},
	success: function(data) {
			var links = new Array();
			for(var triple in data){
				var link = {source: getLable(data[triple].subject), target: getLable(data[triple].object), label: getLable(data[triple].predicate)};
				links.push(link);

			}
// 			var links = [
// 			  {source: "Microsoft", target: "HTC", label: "licensing"},
// 			  {source: "Microsoft", target: "HTC", label: "suit"},
// 			  {source: "Microsoft", target: "HTC", label: "resolved"},
// 			  {source: "Samsung", target: "Apple", label: "suit"},
// 			  {source: "Motorola", target: "Apple", label: "suit"},
// 			];
			$('#query-graph').html('');
			render(links);
		}});


}
function getQueryResults(args)
{

	$.ajax({
	url: "/execute/results/"+args,
	type: "GET",
	dataType: "text",
		error: function(response,n, textStatus, exception) {
		alert('Error: ' + response.responseText);
		console.log(n);
		console.log(textStatus);

	},
	success: function(data){

                        var rframe = document.getElementById('results');
			//xml = data.replace(/</gim,'&lt;').replace(/>/gim,'&gt;');
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
	url: "/execute/getquery/",
	type: "GET",
	dataType: "text",
	data: d,
	success: function(m) {
		obj.text(m);
		obj.html(obj.html().replace(/\n/g,'&nbsp;<br/>'));
		obj.html(obj.html().replace(/\t/g,'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'));
		obj.bind('DOMNodeInserted DOMSubtreeModified DOMNodeRemoved', function(event) {
		document.getElementById("queryDisplay").selectedIndex = 19;
		});
	}
	});
}

function displayLoaders(show){
        var graph = document.getElementById('query-graph');
        var time = document.getElementById('time');
        var results = document.getElementById('results');
	var note = document.getElementById('note');
	var filter_time = document.getElementById('filter_time');

if(show){
	var ldrImgResults = "<img src='/static/images/ajax-loader-yellow.gif' style='display: block;margin: auto; margin-top:5px;'/>";
	var ldrImg1 = "<img src='/static/images/ajax-loader-yellow.gif' style='display: block;margin: auto; margin-top:118px;'/>";
	var ldrImg2 = "<img src='/static/images/ajax-loader-yellow.gif' style='display: block;margin: auto; margin-top:100px;'/>";

	results.innerHTML=ldrImgResults;
	time.innerHTML=ldrImg2;
	graph.innerHTML=ldrImg1;
        note.innerHTML="";
	filter_time.innerHTML="";
}
else{
        results.innerHTML='';
        time.innerHTML='';
        graph.innerHTML='';
        note.innerHTML='';
	filter_time.innerHTML="";

}
}

function getStatusUpdates(index,qId,cache,opt){

		var isDone='false';
		var args = '?queryId='+qId+'&cache='+cache+'&opt='+opt+'&index='+index;
		var known = false;

		if (qId=='BTC10' ||qId=='BTC11'||qId=='LUBM1'|| qId=='LUBM2'|| qId=='LUBM3') {
			
		
					getQueryResults(args);
					getQueryTimimgs(args);
					getQueryGraph(args);
					return;
		}

		var url = "/execute/getstatus/?verbose=false";
		isDone=httpGet(url)

		if(isDone == 'false') {
		       setTimeout(getStatusUpdates, 10000); /* this checks the flag every 100 milliseconds*/
		} else {

				displayLoaders(false);
				if(isDone=='true'){
					getQueryResults(args);
					getQueryTimimgs(args);
					getQueryGraph(args);
					setTimeout(function(){ alertReveal(); }, 3000);
				}
				else
					alert('Query Execution Error');
    	}




}
//Jevascript to run RIQ
function runRIQ(e)
{
	//display loader img
	displayLoaders(true);

	document.getElementById("query-text").value= $('#query').html().replace(/<br\s*[\/]?>|&nbsp;/gi,' ').replace(/&lt;/gi,' <').replace(/&gt;/gi,'> ');
	var form = document.getElementById("frmRIQ");
	var formURL = form.action;
	var postData = $('#frmRIQ').serialize();

	$.ajax({
	url: '/execute/',
	type: "POST",
	dataType: "text",
	data: postData,
	timeout: 9000000000,
	success: function(m,response,textStatus) {
		console.log("Execute Form Submitted Successfully");

		console.log(textStatus.responseText);
		//get index name
		var ndx = document.getElementById("indexname");
		ndx = ndx.options[ndx.selectedIndex].value;

		//get query id
		var e = document.getElementById("queryDisplay");
		var qId = e.options[e.selectedIndex].value;

		//get selected cache
		var cache ='warm';
		var radios = document.getElementsByName('typecache');
		for (var i = 0; i < radios.length; i++) {

			if (radios[i].type === 'radio' && radios[i].checked) {
				cache = radios[i].value;
			}
		}

		//get selected optimization
		var opt ='opt';
		var radios = document.getElementsByName('optimizationtype');
		for (var i = 0; i < radios.length; i++) {
			if (radios[i].type === 'radio' && radios[i].checked) {
				opt = radios[i].value;
			}
		}


		getStatusUpdates(ndx,qId,cache,opt);

	},
	error: function(response,n, textStatus, exception) {
		alert("ERORR: "+response.responseText);
		console.log(n);
		console.log(exception);
		console.log(textStatus);
		displayLoaders(false);

	}
	});

}

function alertReveal()
{/*
	Disable for video
                document.getElementById("notify_title").innerHTML='Querying Details';
                document.getElementById("notify_content").innerHTML="You can navigate to Visualize Query to see more interesting data about the execution";
		document.getElementById('myModalLink').click();

*/
}
