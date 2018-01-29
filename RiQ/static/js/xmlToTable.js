 function parseXML(val) {
  if (document.implementation && document.implementation.createDocument) {
   xmlDoc = new DOMParser().parseFromString(val, 'text/xml');
  } else if (window.ActiveXObject) {
   xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
   xmlDoc.loadXML(val);
  } else {
   alert("Your browser can't handle this script");
   return null;
  }
  return xmlDoc;
 }

 function ConvertToTable(xmlDoc) {
  var table = '<table class="tg">'
   //create table header with variable names
  var vars = xmlDoc.getElementsByTagName('variable');
  var V = [];
  table += '<tr>'
  for (var i = 0; i < vars.length; i++) {
   var v = vars[i].getAttribute('name');
   V.push(v)
   table += '<th class="tg-9hbo">' + v + '</th>'
  }

  table += '</tr>'

  //create table content
  var results = xmlDoc.getElementsByTagName('result');
  for (var i = 0; i < results.length; i++) {
   table += '<tr>'
   var bindings = results[i].getElementsByTagName('binding');
   //for every binding generate cell
   if (bindings.length == V.length) {
    for (var b = 0; b < bindings.length; b++) {
     table += '<td class="tg-yw4l">' + bindings[b].textContent + '</td>';
    }
   } else {
    var c = 0;
    for (var b = 0; b < V.length; b++) {
     if (c < bindings.length) {

      if (V[b] == bindings[c].getAttribute('name')) {
       table += '<td class="tg-yw4l">' + bindings[c].textContent + '</td>';
       c++;
      } else {
       table += '<td class="tg-yw4l"></td>';
      }

     }

    }
   }
   table += '</tr>'
  }

  table += '</table>'
  return table;
 }

