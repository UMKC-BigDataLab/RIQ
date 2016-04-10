function render(links){

	//ref: http://jsfiddle.net/7HZcR/3/
	//     https://github.com/zhanghuancs/D3.js-Node-MultiLinks-Node

	//sort links by source, then target
	links.sort(function(a,b) {
		if (a.source > b.source) {return 1;}
		else if (a.source < b.source) {return -1;}
		else {
			if (a.target > b.target) {return 1;}
			if (a.target < b.target) {return -1;}
			else {return 0;}
		}
	});



	var mLinkNum ={};
    for (var i = 0; i < links.length; i++)
    {
        if (i != 0 &&
            links[i].source == links[i-1].source &&
            links[i].target == links[i-1].target)
        {
            links[i].linkindex = links[i-1].linkindex + 1;
        }
        else
        {
            links[i].linkindex = 1;
        }
        // save the total number of links between two nodes
        if(mLinkNum[links[i].target + "," + links[i].source] !== undefined)
        {
            mLinkNum[links[i].target + "," + links[i].source] = links[i].linkindex;

        }
        else
        {
            mLinkNum[links[i].source + "," + links[i].target] = links[i].linkindex;

        }
    }

	//any links with duplicate source and target get an incremented 'linknum'
	for (var i=0; i<links.length; i++) {
		if (i != 0 &&
			links[i].source == links[i-1].source &&
			links[i].target == links[i-1].target) {
				links[i].linknum = links[i-1].linknum + 1;
			}
		else {links[i].linknum = 1;};

	};

	var nodes = {};

	// Compute the distinct nodes from the links.
	links.forEach(function(link) {
	  link.source = nodes[link.source] || (nodes[link.source] = {name: link.source});
	  link.target = nodes[link.target] || (nodes[link.target] = {name: link.target});
	});


	var width = 850,
		height = 600;

	var force = d3.layout.force()
		.nodes(d3.values(nodes))
		.links(links)
		.size([width, height])
		.linkDistance(200)
		.charge(-300)
		.on("tick", tick)
		.start();

	var drag = force.drag()
		.on("dragstart", dragstart);

	var svg = d3.select("#query-graph").append("svg")
		.attr("width", width)
		.attr("height", height);

	// Per-type markers, as they don't inherit styles.
	svg.append("defs").selectAll("marker")
		.data(force.links())
		.enter().append("marker")
		.attr("id", function(d) { return d.label; })
		.attr("viewBox", "0 -5 10 10")
		.attr("refX", 43)
		.attr("refY", -4)
		.attr("markerWidth", 8)
		.attr("markerHeight", 6)
		.attr("orient", "auto")
	  	.append("path")
		.attr("d", "M0,-5L10,0L0,5");




	var path = svg.append("g").selectAll("path")
		.data(force.links())
		.enter().append("path")
		.attr("class", function(d) { return "link " + d.label; })
		.attr("marker-end", function(d) { return "url(#" + d.label + ")"; })
		.attr("id",function(d,i) { return "linkId_" + i; });

	var labelText = svg.selectAll(".labelText")
		.data(force.links())
		.enter().append("text")
		.style("text-anchor","end")
		.style("fill","red")
		.style("font-weight","bold")
		.attr("class","labelText")
	//	.attr("dx",30)
		.attr("dy",-4)
		.append("textPath")
		.attr("xlink:href",function(d,i) { return "#linkId_" + i;})
		.attr("startOffset","50%")
		.text(function(d,i) { return d.label;});

	var circle = svg.append("g").selectAll("circle")
		.data(force.nodes())
		.enter().append("circle")
		.attr("r", 30)
		.call(force.drag);



	var text = svg.append("g").selectAll("text")
		.data(force.nodes())
		.enter().append("text")
		.style("font-size", "11px")
		.style("font-weight","bold")
		.attr("x", 0)
		.attr("y", 0)
		.attr("dy", ".35em")
		.attr("text-anchor", "middle")
		.text(function(d) { return d.name; });



// Use elliptical arc path segments to doubly-encode directionality.
function tick() {
  path.attr("d", function(d) {
	var dx = d.target.x - d.source.x,
        dy = d.target.y - d.source.y,
        dr = Math.sqrt(dx * dx + dy * dy);


    // get the total link numbers between source and target node
    var lTotalLinkNum = mLinkNum[d.source.name+ "," + d.target.name] || mLinkNum[d.target.name + "," + d.source.name];

    if(lTotalLinkNum > 1)
    {
        // if there are multiple links between these two nodes, we need generate different dr for each path
        dr = dr/(1 + (1/lTotalLinkNum) * (d.linkindex - 1));
    }
    // generate svg path
    return "M" + d.source.x + "," + d.source.y +

           " A" + dr + "," + dr + " 0 0, 1" + d.target.x + "," + d.target.y;
  });

  circle.attr("transform", function(d) {
    return "translate(" + d.x + "," + d.y + ")";
  });

  text.attr("transform", function(d) {
    return "translate(" + d.x + "," + d.y + ")";
  });

}

function dragstart(d) {
  d3.select(this).classed("fixed", d.fixed = true);
}

}