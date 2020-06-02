<template>
<div class="signup-wrapper pt-12">
	<v-row justify="center">
		<v-col cols="12" sm="8" md="6">
			<v-container class="form-structor">
				<v-form ref="signupForm" v-model="signupValid" lazy-validation @submit.prevent>
					<div class="signup slide-up">
						<h2 class="form-title" id="signup"><span>or</span>Sign up</h2>
						<div class="form-holder">
							<v-text-field
								type="text" 
								class="input"
								color="black"
								label="Name" 
								v-model="name"
								:rules="nameRules"
								required
								/>
							<v-text-field 
								type="email" 
								class="input" 
								color="black"
								label="Email" 
								v-model="email"
								:rules="emailRules"
								required
								/>
							<v-text-field
								type="password"
								class="input" 
								color="black"
								label="Password" 
								v-model="password"
								:rules="passwordRules"
								required
								/>
							<v-text-field
								type="tel" 
								class="input"
								id="contactInput"
								maxlength="13"
								color="black"
								pattern="[\+]\d{2}[\(]\d{2}[\)]\d{4}[\-]\d{4}"
								v-model="contact"
								:rules="contactRules"
								required
								/>
							<v-row>
								<v-col cols="6" sm="4">

									<v-text-field
									type="text" 
									class="input" 
									color="black"
									label="00시" 
									v-model="addressCity"
									:rules="addressRules"
									required
									/>
								</v-col>
								<v-col cols="6" sm="4">

									<v-text-field
									type="text" 
									class="input" 
									color="black"
									label="00구" 
									v-model="addressGu"
									:rules="addressRules"
									required
									/>
								</v-col>
								<v-col cols="6" sm="4" class="dongInput">

									<v-text-field
									type="text" 
									class="input" 
									color="black"
									label="00동" 
									v-model="addressDong"
									:rules="addressRules"
									required
									/>
								</v-col>
							</v-row>
						</div>
						<button class="submit-btn" @click="signup">Sign up</button>
					</div>
				</v-form>
				<v-form ref="loginForm" v-model="loginValid" lazy-validation @submit.prevent>
					<div class="login">
						<div class="center">
							<h2 class="form-title" id="login"><span>or</span>Log in</h2>
							<div class="form-holder">
								<v-text-field
									type="email" 
									class="input"
									color="#8c28b4" 
									label="Email"
									v-model="loginId"
									:rules="loginEmailRules"
									required
								/>
								<v-text-field
									type="password" 
									class="input" 
									color="#8c28b4" 
									label="Password" 
									v-model="loginPw"
									:rules="loginPwRules"
									required
									/>
							</div>
							<button class="submit-btn" @click="login">Log in</button>
						</div>
					</div>
				</v-form>
			</v-container>
		</v-col>
	</v-row>
</div>
</template>

<script>
	import * as EmailValidator from 'email-validator'
	import baseURL from "@/base-url"
	import cookie from "@/cookie"

	export default {
		data: () => ({
			signupValid: false,
			loginValid: false,
			name: '',
			nameRules: [
        v => !!v || '이름을 입력해주세요',
      ],
			email: '',
			emailRules: [
        v => !!v || '이메일을 입력해주세요',
        v => (EmailValidator.validate(v)) || '이메일 형식이 아닙니다',
      ],
			password: '',
			passwordRules: [
        v => !!v || '비밀번호를 입력해주세요',
        v => v.length >= 8 || '8자 이상의 비밀번호를 입력해주세요',
      ],
			contact: '',
			contactRules: [
				v => !!v || '연락처를 입력해주세요'
			],
			addressCity: '',
			addressGu: '',
			addressDong: '',
			addressRules: [
				v => !!v || '입력해주세요'
			],
			loginId: '',
			loginEmailRules: [
        v => !!v || 'ID를 입력해주세요',
        v => (EmailValidator.validate(v)) || '이메일 형식이 아닙니다',
      ],
			loginPw: '',
			loginPwRules: [
        v => !!v || '비밀번호를 입력해주세요',
        v => v.length >= 8 || '8자 이상의 비밀번호를 입력해주세요',
      ],
		}),
		mounted() {
			const loginBtn = document.getElementById('login');
			const signupBtn = document.getElementById('signup');

			loginBtn.addEventListener('click', (e) => {
				const parent = e.target.parentNode.parentNode;
				Array.from(e.target.parentNode.parentNode.classList).find((element) => {
					if(element !== "slide-up") {
						parent.classList.add('slide-up')
					}else{
						signupBtn.parentNode.classList.add('slide-up')
						parent.classList.remove('slide-up')
					}
				});
			});
				
			signupBtn.addEventListener('click', (e) => {
				const parent = e.target.parentNode;
				Array.from(e.target.parentNode.classList).find((element) => {
					if(element !== "slide-up") {
						parent.classList.add('slide-up')
					}else{
						loginBtn.parentNode.parentNode.classList.add('slide-up')
						parent.classList.remove('slide-up')
					}
				});
			});

			document.getElementById('contactInput').addEventListener('input', function (e) {
				const x = e.target.value.replace(/\D/g, '').match(/(\d{0,3})(\d{0,4})(\d{0,4})/);
				e.target.value = !x[2] ? x[1] : x[1] + '-' + x[2] + (x[3] ? '-' + x[3] : '');
			});
		},
		methods: {
			signup() {
				if (this.$refs.signupForm.validate()) {
					const data = {
						email: this.email,
						location: this.addressCity + '시 ' + this.addressGu + '구 ' + this.addressDong + '동',
						name: this.name,
						password: this.password,
						phone: this.contact
					}
					baseURL.post('signup', data)
						.then(() => {
							this.$router.push({
								name: "Main"
							})
						})
						.catch(() => {
							alert("잘못된 시도입니다.")
						})
				}
			},
			login() {
				if (this.$refs.loginForm.validate()) {
					const data = {
						email: this.loginId,
						password: this.loginPw
					}
					baseURL.post('signin', data)
						.then(res => {
							const loginData = {
								token: res.data,
								id: res.data,
								location: res.data,
								name: res.data,
								password: res.data,
								phone: res.data
							}
							cookie.setCookie(loginData);
							this.$store.commit('startLogin', loginData)
              this.$store.commit('isLogin')
              this.$router.push({
                name: "Main"
              })
						})
				}
			}
		}
  }
</script>

<style lang="scss">

.signup-wrapper {
	width: 100%;
	height: 100%;
	background-image: url('../../assets/images/signBack.jpg') !important;
	background-size: 100% 100%;
}
.form-structor {
	// background-color: rgb(249, 240, 255);
	border: 1px solid #fff;
	border-bottom: none;
	// border: 2px solid #df4c73;
	border-radius: 15px;
	height: 680px;
	// width: 350px;
	position: relative;
	overflow: hidden;
	
	&::after {
		content: '';
		opacity: .8;
		position: absolute;
		top: 0;right:0;bottom:0;left:0;
		background-repeat: no-repeat;
		background-position: left bottom;
		background-size: 750px;
		// background-image: url('https://user-images.githubusercontent.com/52478972/82554559-25890c80-9ba1-11ea-9aaf-ce62ddd1cf1c.jpg');
	}
	
	.signup {
		position: absolute;
		top: 45%;
		left: 50%;
		-webkit-transform: translate(-50%, -50%);
		width: 65%;
		z-index: 1;
		-webkit-transition: all .3s ease;
		
		
		&.slide-up {
			top: 5%;
			-webkit-transform: translate(-50%, 0%);
			-webkit-transition: all .3s ease;
		}
		
		&.slide-up .form-holder,
		&.slide-up .submit-btn {
			opacity: 0;
			visibility: hidden;
		}
		
		&.slide-up .form-title {
			font-size: 1em;
			cursor: pointer;
		}
		
		&.slide-up .form-title span {
			margin-right: 5px;
			opacity: 1;
			visibility: visible;
			-webkit-transition: all .3s ease;
		}
		
		.form-title {
			color: rgb(0, 0, 0);
			font-size: 1.7em;
			text-align: center;
			
			span {
				color: rgba(0,0,0,0.4);
				opacity: 0;
				visibility: hidden;
				-webkit-transition: all .3s ease;
			}
		}
		
		.form-holder {
			border-radius: 15px;
			background-color: rgba(255, 255, 255, 0.301);
			min-height: 300px;
			max-height: 400px;
			overflow: hidden;
			margin-top: 50px;
			opacity: 1;
			visibility: visible;
			-webkit-transition: all .3s ease;
			
			.input {
				border: 0;
				outline: none;
				box-shadow: none;
				display: block;
				height: 50px;
				line-height: 30px;
				padding: 8px 15px;
				// border-bottom: 1px solid rgb(238, 238, 238);
				width: 100%;
				font-size: 15px;
				color:rgb(63, 63, 63);
				font-weight: bold;
				&:last-child {
					border-bottom: 0;
				}
				&::-webkit-input-placeholder {
					color: rgba(0,0,0,0.4);
				}
			}
			.row {
				padding: 8px 15px !important;
				.col-6 {
					padding: 0 !important;
				}
			}
		}
		
		.submit-btn {
			background-color: rgba(255, 255, 255, 0.349);
			color: rgba(256,256,256,0.7);
			border: 2px solid rgba(256,256,256,0.7);
			border-radius: 15px;
			display: block;
			margin: 15px auto; 
			padding: 15px 45px;
			width: 100%;
			font-size: 16px;
			color:rgb(63, 63, 63);
			font-weight: bold;
			cursor: pointer;
			opacity: 1;
			visibility: visible;
			-webkit-transition: all .3s ease;
			
			&:hover {
				transition: all .3s ease;
				background-color: #fff;
				font-weight: 900;
				color: #000;
				border: 2px solid #fff;
			}
		}
	}
	
	.login {
		position: absolute;
		top: 20%;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: #fff;
		z-index: 5;
		-webkit-transition: all .3s ease;
		
		&::before {
			content: '';
			position: absolute;
			left: 50%;
			top: -20px;
			-webkit-transform: translate(-50%, 0);
			background-color: #fff;
			border-top: 2px solid #8c28b4;
			width: 200%;
			height: 250px;
			border-radius: 50%;
			z-index: 4;
			-webkit-transition: all .3s ease;
		}
		
		.center {
			position: absolute;
			top: calc(50% - 10%);
			left: 50%;
			-webkit-transform: translate(-50%, -50%);
			width: 65%;
			z-index: 5;
			-webkit-transition: all .3s ease;
			
			.form-title {
				color: #000;
				font-size: 1.7em;
				text-align: center;

				span {
					color: rgba(0,0,0,0.4);
					opacity: 0;
          visibility: hidden;
          -webkit-transition: all .3s ease;
				}
			}

			.form-holder {
				height: 130px;
				border-radius: 15px;
				background-color: #fff;
				// border: 1px solid #eee;
				overflow: hidden;
				margin-top: 50px;
				opacity: 1;
				visibility: visible;
				-webkit-transition: all .3s ease;

				.input {
					border: 0;
					outline: none;
					box-shadow: none;
					display: block;
					height: 50px;
					line-height: 30px;
					padding: 8px 15px;
					// border-bottom: 1px solid #eee;
					width: 100%;
					font-size: 15px;
					color:rgb(63, 63, 63);
					font-weight: bold;
					&:last-child {
						border-bottom: 0;
					}
					&::-webkit-input-placeholder {
						color: rgba(0,0,0,0.4);
					}
				}
			}

			.submit-btn {
				background-color: rgb(249, 240, 255);
				border:0;
				border-radius: 15px;
				display: block;
				margin: 15px auto; 
				padding: 15px 45px;
				width: 100%;
				font-size: 16px;
				color:rgb(63, 63, 63);
				font-weight: bold;
				cursor: pointer;
				opacity: 1;
				visibility: visible;
				-webkit-transition: all .3s ease;

				&:hover {
					transition: all .3s ease;
					background-color: #76119e;
					color:rgb(249, 240, 255);
				}
			}
		}
		
		&.slide-up {
			top: 90%;
			-webkit-transition: all .3s ease;
		}
		
		&.slide-up .center {
			top: 10%;
			-webkit-transform: translate(-50%, 0%);
			-webkit-transition: all .3s ease;
		}
		
		&.slide-up .form-holder,
		&.slide-up .submit-btn {
			opacity: 0;
			visibility: hidden;
			-webkit-transition: all .3s ease;
		}
		
		&.slide-up .form-title {
			font-size: 1em;
			margin: 0;
			padding: 0;
			cursor: pointer;
			-webkit-transition: all .3s ease;
		}
		
		&.slide-up .form-title span {
			margin-right: 5px;
			opacity: 1;
			visibility: visible;
			-webkit-transition: all .3s ease;
		}
	}
}
</style>