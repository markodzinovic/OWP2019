$(document).ready(function() {
	$('#odjava').on('click', function() {
		$.get('LogoutServlet', function(data) {
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		})
		
	})
	
	var korisnickoIme = $('#korisnickoIme');
	var lozinka = $('#lozinka');
	var datum = $('#datum');
	
	var porukaParagraf = $('#porukaParagraf');
	
	$.get('ProfilServlet', function(data) {
		console.log(data.status);
		
		if(data.status == 'unauthenticated'){
			console.log(data)
			window.location.replace('Login.html');
			return;
		}
		if(data.status == 'success'){
			var profil = data.profil;
			
			korisnickoIme.val(profil.korisnickoIme);
			lozinka.val(profil.lozinka);
			datum.val(profil.datumRegistracije);
			
			$('#izmenaKorisnika').on('click', function() {
				izmenaProfila();
			})
			
		}
	})
	
	function izmenaProfila() {
		
		var lozinkaIzmena = lozinka.val();
		
		params = {
				'lozinkaIzmena' : lozinkaIzmena
		}
		
		$.post('ProfilServlet', params, function(data) {
			
			if(data.status == 'failure'){
				porukaParagraf.text(data.poruka);
				return;
			}
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		})
	}
})
