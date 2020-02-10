$(document).ready(function() {
	$('#odjava').on('click', function() {
		$.get('LogoutServlet', function(data) {
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		})
		
	})
	
	var idProjekcije = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(idProjekcije);
	
	var filmP = $('#filmP');
	var datumP = $('#datumP');
	var tipP = $('#tipP');
	var salaP = $('#salaP');
	var cenaP = $('#cenaP');
	var slobodnaSedistaP = $('#sedistaP');
	
	var porukaParagraf = $('#porukaParagraf');
	
	var kupiKartu = $('#kupiKartuP');
	var adminBrisanje = $('#adminBrisanje');
	

	adminBrisanje.hide();
	
	param = {
			'idProjekcije' : idProjekcije
	}
	
	$.get('PojedinacnaProjekcijaServlet',param, function(data) {
		
		console.log(data.status);
		
		if(data.status == 'unauthenticated'){
			window.location.replace('Login.html');
			return;
		}
		
		if(data.status == 'success'){
			console.log(data.uloga);
			
			console.log(data.projekcija);
			
			var projekcija = data.projekcija;
			
			filmP.val(projekcija.nazivFilma.naziv);
			datumP.val(projekcija.datum);
			tipP.val(projekcija.tipProjekcije.naziv);
			salaP.val(projekcija.sala.naziv);
			cenaP.val(projekcija.cena);
			slobodnaSedistaP.val(10);

			if(projekcija.obrisan == true){
				kupiKartu.hide();
				adminBrisanje.hide();
				return;
			}
			
			kupiKartu.on('click', function() {
				window.location.replace('KupovinaKarte.html?id='+idProjekcije);
			})

			if(data.uloga == 'ADMIN'){

				adminBrisanje.show();
				admin();
				return;
			}
		}
	})
	
	function admin() {
		adminBrisanje.on('click', function() {
			
			params = {
					'akcija' : 'brisanje',
					'idProjekcije' : idProjekcije
			}
			console.log(idProjekcije);
			$.post('PojedinacnaProjekcijaServlet', params , function(data) {
				console.log(data);
				if(data.status == 'failure'){
					porukaParagraf.text(data.poruka);
					adminBrisanje.hide();
					kupiKartu.hide();
					return;
				}
				if(data.status == 'success'){
					window.location.replace('Glavna.html');
					return;
				}
			})
		})
	}
		
})