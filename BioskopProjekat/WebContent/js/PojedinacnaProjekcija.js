$(document).ready(function() {
	
	var idProjekcije = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(idProjekcije);
	
	var filmP = $('#filmP');
	var datumP = $('#datumP');
	var tipP = $('#tipP');
	var salaP = $('#salaP');
	var cenaP = $('#cenaP');
	var slobodnaSedistaP = $('#sedistaP')
	
	var kupiKartu = $('#kupiKartuP');
	var adminBrisanje = $('#adminBrisanje');
	
	param = {
			'idProjekcije' : idProjekcije
	}
	
	$.get('PojedinacnaProjekcijaServlet',param, function(data) {
		
		console.log(data);
		
		if(data.status == 'success'){
			
			console.log(data.projekcija);
			
			var projekcija = data.projekcija;
			
			filmP.val(projekcija.nazivFilma.naziv);
			datumP.val(projekcija.datum);
			tipP.val(projekcija.tipProjekcije.naziv);
			salaP.val(projekcija.sala.naziv);
			cenaP.val(projekcija.cena);
			slobodnaSedistaP.val(10);
			
		}
	})
	
	adminBrisanje.on('click', function() {
	
		params = {
				'akcija' : 'brisanje',
				'idProjekcije' : idProjekcije
		}
		console.log(idProjekcije);
		$.post('PojedinacnaProjekcijaServlet', params , function(data) {
			console.log(data);
			if(data.status == 'failure'){
				alert('Ne moze da se obrise');
				return;
			}
			if(data.status == 'success'){
				window.location.replace('Glavna.html');
				return;
			}
		})
		
	})
	
	
})