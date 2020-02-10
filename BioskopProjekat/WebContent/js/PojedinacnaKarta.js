$(document).ready(function() {
	$('#odjava').on('click', function() {
		$.get('LogoutServlet', function(data) {
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		})
		
	})
	
	var idKarte = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	var nazivFilma = $('#nazivFilma');
	var datumProjekcije = $('#datumProjekcije');
	var datumKarte = $('#datumKarte');
	var tip = $('#tip');
	var sala = $('#sala');
	var sediste = $('#sediste');
	var cena = $('#cena');
	var korisnik = $('#korisnik');
	
	var porukaParagraf = $('#porukaParagraf');
	var adminKontrola = $('#admin');
	
	adminKontrola.hide();
	
	param = {
			'idKarte' : idKarte
	}
	$.get('PojedinacnaKartaServlet', param, function(data) {
		console.log(data.status);
		
		if(data.status == 'unauthenticated'){
			window.location.replace('Login.html');
			return;
		}
		if(data.status == 'success'){
			console.log(data);
			
			var karta = data.karta;
			
			 nazivFilma.val(karta.projekcija.nazivFilma.naziv);
			 datumProjekcije.val(karta.projekcija.datum);
			 datumKarte.val(karta.datum);
			 tip.val(karta.projekcija.tipProjekcije.naziv);
			 sala.val(karta.projekcija.sala.naziv);
			 sediste.val(karta.sediste);
			 cena.val(karta.projekcija.cena);
			 korisnik.val(karta.korisnik.korisnickoIme);
			 
			 if(data.uloga == 'ADMIN'){
				 adminKontrola.show();
				 adminBrisanjeKarte();
				 return;
			 }
			
		}
	})
	
	function adminBrisanjeKarte() {
		$('#obrisiKartu').on('click', function() {
			
			param = {
					'idKarte' : idKarte
			}
			
			$.post('PojedinacnaKartaServlet', param, function(data) {
				console.log(data);
				
				if(data.status == 'failure'){
					porukaParagraf.text(data.poruka);
					return;
				}
				if(data.status == 'success'){
					window.location.replace('Karte.html');
					return;
				}
			})
		})
	}
	
})