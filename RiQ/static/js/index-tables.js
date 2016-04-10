function showtables(stats){
//	var total = Number(stats.group.build_graph_t)+Number(stats.group.pv_lsh_t)+Number(stats.group.union_t)+Number(stats.group.total_t)+Number(stats.cbf.cbf_t)+Number(stats.split.split_t )+Number(stats.pv_t);
	var total =Number(stats.group.total_t)+Number(stats.cbf.cbf_t)+Number(stats.pv_t);
// 	var t_content = "<table style='width:100%' > ";
// 		t_content += '<tr>';
// 				t_content += "<td style='font-weight:500;'> G BUILD GRAPH</td>"+"<td style='font-weight:400;color:#000;'>" + stats.group.build_graph_t  + '</td>';
// 									t_content += "<td style='font-weight:500;'> G PV LSH</td>"+"<td style='font-weight:400;color:#000;'>" + stats.group.pv_lsh_t  + '</td>';
// 									t_content += "<td style='font-weight:500;'> G UNION</td>"+"<td style='font-weight:400;color:#000;'>" + stats.group.union_t  + '</td>';
// 									t_content += "<td style='font-weight:500;'> G TOTAL</td>"+"<td style='font-weight:400;color:#000;'>" + stats.group.total_t  + '</td>';
// 					t_content += '</tr>'
// 					t_content += '<tr>'
// 									t_content += "<td style='font-weight:500;'> CBF</td>"+"<td style='font-weight:400;color:#000;'>" + stats.cbf.cbf_t  + '</td>';
// 									t_content += "<td style='font-weight:500;'> SPLIT</td>"+"<td style='font-weight:400;color:#000;'>" + stats.split.split_t  + '</td>';
// 									t_content += "<td style='font-weight:500;'> PV CONSTRUCTION</td>"+"<td style='font-weight:400;color:#000;'>" + stats.pv_t  + '</td>';
// 									t_content += "<td style='font-weight:500;'> TOTAL</td>"+"<td style='font-weight:400;color:#000;'>" + total.toFixed(2)  + '</td>';
// 					t_content += '</tr>';
// 	t_content += "</table>";

	var pv_content = "<table style='width:100%;text-align: center;' > ";
			pv_content += '<tr style="background:#E87352;color:#FFF;  border-top: 1px solid #000;  border-left: 1px solid #000;border-right: 1px solid #000;">';
									pv_content += "<td style='font-weight:500;border-right: 1px solid #000;'> Total # of Quads</td>";
									pv_content += "<td style='font-weight:500;border-right: 1px solid #000;'> Avg # of Quads/Graph</td>";
									pv_content += "<td style='font-weight:500;'> Max # of Quads/Graph</td>";
			pv_content += '</tr>';
			pv_content += '<tr style="border-left: 1px solid #000;border-right: 1px solid #000;">';

									pv_content += "<td style='font-weight:400;color:#000;border-right: 1px solid #000;'>" + stats["Total size"]  + '</td>';
									pv_content += "<td style='font-weight:400;color:#000;border-right: 1px solid #000;'>" + stats["Avg graph size"]  + '</td>';
									pv_content += "<td style='font-weight:400;color:#000;'>" + stats["Max graph size"]  + '</td>';

			pv_content += '<tr style="background:#E87352;color:#FFF;border-left: 1px solid #000;border-right: 1px solid #000;">';
									pv_content += "<td style='font-weight:500;border-right: 1px solid #000;'> Number of Graphs</td>";
 									pv_content += "<td style='font-weight:500;border-right: 1px solid #000;'> PV-Index Size</td>";
 									pv_content += "<td style='font-weight:500;'> Total Time</td>";


			pv_content += '</tr>';

			pv_content += '<tr style="border-bottom: 1px solid #000;border-left: 1px solid #000;border-right: 1px solid #000;">';


									pv_content +=  "<td style='font-weight:400;color:#000;border-right: 1px solid #000;'>" + stats["Written graphs"]  + '</td>';
									pv_content += "<td style='font-weight:400;color:#000;border-right: 1px solid #000;'>" + stats["PV Index Size"]   + '</td>';
 									pv_content += "<td style='font-weight:400;color:#000;'>" +total.toFixed(2)   + 's</td>';
			pv_content += '</tr>';

		pv_content += "</table>";



	$(document).ready(function ()
	{
		//$("#timings").html(t_content);
		$("#stats").html(pv_content);
	});
}