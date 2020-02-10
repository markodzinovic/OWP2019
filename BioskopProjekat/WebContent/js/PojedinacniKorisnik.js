$(document).ready(function() {
	$('#odjava').on('click', function() {
		$.get('LogoutServlet', function(data) {
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		})
		
	})
	
	var korisnickoImeGore = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(korisnickoImeGore);
	
	function jedanKorisnik () {
		
		var korisni = $('#korisnickoImeP');
		var datum = $('#datumP');
		
		var param = {
			'korisnickoIme' : korisnickoImeGore
		}
		
		$.get('PojedinacniKorisnikServlet', param , function(data) {
			console.log(data)
			
			if(data.status =='unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
			
			if(data.status == 'success'){
				console.log(data.uloga)
				
				if(data.uloga == 'KORISNIK'){
					window.location.replace('Glavna.html');
					return;
				}
				if(data.uloga == 'ADMIN'){
					
					var kor = data.korisnik;
					
					korisni.val(kor.korisnickoIme);
					datum.val(kor.datumRegistracije);
					console.log(kor.uloga)
					
					var uloga = document.getElementById("ulogaB");
					
					uloga.value = kor.uloga;
					admin();
					
					if(kor.obrisan == true){
						$('#izmenaKorisnika').hide();
						$('#brisanjeKorisnika').hide();
						$('#ulogaB').prop('disabled', true);
						return;
					}
					return;
				}
				
				
			}
			
			
			
		})
		
	}
	
	function admin () {
		
		$('#izmenaKorisnika').on('click', function() {
			var k = $('#korisnickoImeP');
			var datumR = $('#datumP');
			var u = document.getElementById("ulogaB");
			
			var korisnickoIme = k.val()
			var datumRegistracije = datumR.val()
			var uloga = u.value
			
			
			console.log(korisnickoIme)
			console.log(datumRegistracije)
			console.log(uloga)
			
			 params = {
				'akcija' : 'izmena',
				'korisnickoIme' : korisnickoImeGore,
				'datumRegistracije' : datumRegistracije,
				'uloga' : uloga
			}

			
			$.post('PojedinacniKorisnikServlet', params, function(data) {
				
				if(data.status == 'failure'){
					alert(data.poruka);
					return;
				}
				
				if (data.status == 'success') {
					window.location.replace('Korisnici.html')
					return;
				}
			
				
			})
			
			
		})
		
		$('#brisanjeKorisnika').on('click', function() {
		
		param = {
			'akcija' : 'brisanje',
			'korisnickoIme' : korisnickoImeGore
		}
		$.post('PojedinacniKorisnikServlet', param , function(data) {
			
			if(data.status == 'failure'){
				$('#izmenaKorisnika').hide();
				$('#brisanjeKorisnika').hide();
				$('#ulogaB').prop('disabled', true);
				alert(data.poruka)
				return;
			}
			
			if (data.status == 'success') {
				window.location.replace('Korisnici.html')
				return;
			}
		} )
		
	} )
		
	}

	jedanKorisnik();
})