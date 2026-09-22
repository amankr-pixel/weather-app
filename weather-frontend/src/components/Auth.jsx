
import {useState} from "react";
import {loginUser, registerUser} from "../services/weatherService";

function Auth( { onLogin} ) {

    const [isLogin, setIsLogin] = useState(true);

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        setMessage("");
        setError("");

        try{

            if(isLogin) {

                const data = await loginUser({
                    email,
                    password
                });

                localStorage.setItem("token", data.token);
                localStorage.setItem("username", data.username);

                onLogin(data);

            } else {

                const result = await registerUser({
                    username,
                    email,
                    password
                });

                setMessage(result);

                setIsLogin(true);
                setUsername("");
                setPassword("");
            }

        } catch (err) {

            if (err.response && err.response.data) {
                    setError(err.response.data);
                  } else {
                    setError("Something went wrong");
                  }
        }
    };

    return (
        <div className="auth-container">

          <div className="auth-card">

            <h2>
              {isLogin ? "🔐 Login" : "📝 Create Account"}
            </h2>

            <form onSubmit={handleSubmit}>

              {!isLogin && (
                <input
                  type="text"
                  placeholder="Username"
                  value={username}
                  onChange={(e) =>
                    setUsername(e.target.value)
                  }
                  required
                />
              )}

              <input
                type="email"
                placeholder="Email"
                value={email}
                onChange={(e) =>
                  setEmail(e.target.value)
                }
                required
              />

              <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) =>
                  setPassword(e.target.value)
                }
                required
              />

              <button type="submit">
                {isLogin ? "Login" : "Register"}
              </button>

            </form>

            {message && (
              <p className="success-message">
                {message}
              </p>
            )}

            {error && (
              <p className="auth-error">
                {error}
              </p>
            )}

            <button
              className="switch-auth"
              onClick={() => {
                setIsLogin(!isLogin);
                setMessage("");
                setError("");
              }}
            >
              {isLogin
                ? "Create a new account"
                : "Already have an account? Login"}
            </button>

          </div>

        </div>
    );
}

export default Auth;